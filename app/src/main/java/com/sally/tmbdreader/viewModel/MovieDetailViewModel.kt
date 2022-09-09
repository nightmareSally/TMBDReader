package com.sally.tmbdreader.viewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.sally.tmbdreader.repository.AccountRepository
import com.sally.tmbdreader.repository.MovieRepository
import com.sally.tmbdreader.util.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val id: Int,
    private val sharedPreference: SharedPreference,
    repository: MovieRepository,
    private val accountRepository: AccountRepository,
    application: Application
) : BaseViewModel(application) {
    val movie = repository.getMovieWithFavorite(id)

    private val favoriteChannel = Channel<Job>(capacity = Channel.UNLIMITED).apply {
        viewModelScope.launch {
            consumeEach { it.join() }
        }
    }

    fun setFavorite() {
        val accountId = sharedPreference.accountId ?: return
        val sessionId = sharedPreference.sessionId ?: return
        favoriteChannel.trySend(
            viewModelScope.launch(Dispatchers.IO) {
                val isFavorite = accountRepository.checkIsFavorite(id)
                accountRepository.setFavorite(accountId, sessionId, id, !isFavorite)
            })
    }
}