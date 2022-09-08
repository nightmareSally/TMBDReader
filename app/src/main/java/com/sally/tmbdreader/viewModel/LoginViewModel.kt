package com.sally.tmbdreader.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sally.tmbdreader.SingleLiveEvent
import com.sally.tmbdreader.model.AccountData
import com.sally.tmbdreader.model.RequestTokenData
import com.sally.tmbdreader.repository.AccountRepository
import com.sally.tmbdreader.util.Extensions.onApiError
import com.sally.tmbdreader.util.Extensions.onNetworkError
import com.sally.tmbdreader.util.Extensions.onSuccess
import com.sally.tmbdreader.util.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val sharedPreference: SharedPreference,
    private val repository: AccountRepository,
    application: Application
) : BaseViewModel(application) {

    val userName: LiveData<String>
        get() = _userName
    private val _userName = MutableLiveData<String>()

    val password: LiveData<String>
        get() = _password
    private val _password = MutableLiveData<String>()

    val onLoginSuccess: LiveData<Unit>
        get() = _onLoginSuccess
    private val _onLoginSuccess = SingleLiveEvent<Unit>()

    fun setUserName(userName: String) {
        _userName.value = userName
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun login() {
        val userName = userName.value ?: return
        val password = password.value ?: return
        viewModelScope.launch {
            loading.value = true
            withContext(Dispatchers.IO) {
                getRequestToken(userName, password)
            }
            loading.value = false
        }
    }

    private suspend fun getRequestToken(userName: String, password: String) {
        repository.getRequestToken().onSuccess {
            loginWithUserData(userName, password, it.token)
        }.onApiError { code, message ->
            apiError.postValue(Pair(code, message))
        }.onNetworkError { message ->
            networkError.postValue(message)
        }
    }

    private suspend fun loginWithUserData(userName: String, password: String, token: String) {
        repository.login(userName, password, token)
            .onSuccess {
                createSession(it.token)
            }.onApiError { code, message ->
                apiError.postValue(Pair(code, message))
            }.onNetworkError { message ->
                networkError.postValue(message)
            }
    }

    private suspend fun createSession(token: String) {
        repository.createSession(token)
            .onSuccess {
                getAccountData(it.sessionId)
            }.onApiError { code, message ->
                apiError.postValue(Pair(code, message))
            }.onNetworkError { message ->
                networkError.postValue(message)
            }
    }

    private suspend fun getAccountData(sessionId: String) {
        repository.getAccountDetail(sessionId)
            .onSuccess {
                sharedPreference.sessionId = sessionId
                sharedPreference.accountId = it.id
                getFavoriteMovies(it.id, sessionId)
            }.onApiError { code, message ->
                apiError.postValue(Pair(code, message))
            }.onNetworkError { message ->
                networkError.postValue(message)
            }
    }

    private suspend fun getFavoriteMovies(accountId: Int, sessionId: String, page: Int = 1) {
        repository.getFavoriteMovies(accountId, sessionId, page)
            .onSuccess {
                if (it.totalPage != 0 && it.page != it.totalPage) {
                    getFavoriteMovies(accountId, sessionId, it.page)
                } else {
                    _onLoginSuccess.postValue(Unit)
                }
            }.onApiError { code, message ->
                clearData()
                apiError.postValue(Pair(code, message))
            }.onNetworkError { message ->
                clearData()
                networkError.postValue(message)
            }
    }

    suspend fun clearData() {
        sharedPreference.accountId = null
        sharedPreference.sessionId = null
        repository.clearFavorite()
    }
}