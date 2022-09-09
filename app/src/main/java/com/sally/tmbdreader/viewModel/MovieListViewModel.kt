package com.sally.tmbdreader.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sally.tmbdreader.SingleLiveEvent
import com.sally.tmbdreader.repository.AccountRepository
import com.sally.tmbdreader.repository.MovieRepository
import com.sally.tmbdreader.util.Extensions.onApiError
import com.sally.tmbdreader.util.Extensions.onNetworkError
import com.sally.tmbdreader.util.Extensions.onSuccess
import com.sally.tmbdreader.util.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val sharedPreference: SharedPreference,
    private val accountRepository: AccountRepository,
    private val movieRepository: MovieRepository,
    application: Application
) : BaseViewModel(application) {
    private var currentPage: Int = 0
    private var totalPage: Int = -1
    private var movieJob: Job? = null

    val movies = movieRepository.moviesWithFavorite

    val onLogoutSuccess: LiveData<Unit>
        get() = _onLogoutSuccess
    private val _onLogoutSuccess = SingleLiveEvent<Unit>()

    private val favoriteChannel = Channel<Job>(capacity = Channel.UNLIMITED).apply {
        viewModelScope.launch {
            consumeEach { it.join() }
        }
    }

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        if (movieJob?.isActive == true) {
            return
        }
        if (currentPage == totalPage) {
            return
        }
        movieJob = viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovie(currentPage + 1)
                .onSuccess {
                    currentPage = it.page
                    if (totalPage == -1) {
                        totalPage = it.totalPage
                    }
                }
        }
    }

    fun setFavorite(movieId: Int) {
        val accountId = sharedPreference.accountId ?: return
        val sessionId = sharedPreference.sessionId ?: return
        favoriteChannel.trySend(
            viewModelScope.launch(Dispatchers.IO) {
                val isFavorite = accountRepository.checkIsFavorite(movieId)
                accountRepository.setFavorite(accountId, sessionId, movieId, !isFavorite)
            })
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepository.clearFavorite()
            movieRepository.deleteMovies()
            sharedPreference.accountId = null
            sharedPreference.sessionId = null
            _onLogoutSuccess.postValue(Unit)
        }
    }
}