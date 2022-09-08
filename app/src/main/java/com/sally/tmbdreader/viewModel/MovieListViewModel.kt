package com.sally.tmbdreader.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sally.tmbdreader.repository.AccountRepository
import com.sally.tmbdreader.repository.MovieRepository
import com.sally.tmbdreader.util.Extensions.onApiError
import com.sally.tmbdreader.util.Extensions.onNetworkError
import com.sally.tmbdreader.util.Extensions.onSuccess
import com.sally.tmbdreader.util.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = accountRepository.checkIsFavorite(movieId)
            accountRepository.setFavorite(accountId, sessionId, movieId, !isFavorite)
        }
    }
}