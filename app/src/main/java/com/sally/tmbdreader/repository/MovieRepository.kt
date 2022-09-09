package com.sally.tmbdreader.repository

import com.sally.tmbdreader.api.ApiResult
import com.sally.tmbdreader.api.service.MovieService
import com.sally.tmbdreader.db.dao.MovieDao
import com.sally.tmbdreader.db.entity.MovieEntity
import com.sally.tmbdreader.model.MovieData
import com.sally.tmbdreader.util.Extensions.onSuccess

class MovieRepository(private val service: MovieService, private val dao: MovieDao) {

    val moviesWithFavorite = dao.getMoviesWithFavorite()

    suspend fun fetchMovie(page: Int): ApiResult<MovieData> {
        return service.getPopularMovie(page)
            .onSuccess { movieData ->
                movieData.results?.let {
                    saveMovieInLocal(it)
                }
            }
    }

    private fun saveMovieInLocal(movies: List<MovieEntity>) {
        dao.insertAll(movies)
    }

    fun getMovieWithFavorite(id: Int) = dao.getMovieWithFavorite(id)

    suspend fun deleteMovies() = dao.deleteAll()
}