package com.sally.tmbdreader.viewModel

import android.app.Application
import com.sally.tmbdreader.repository.MovieRepository

class MovieDetailViewModel(
    private val id: Int,
    private val repository: MovieRepository,
    application: Application
) : BaseViewModel(application) {
    val movie = repository.getMovieWithFavorite(id)
}