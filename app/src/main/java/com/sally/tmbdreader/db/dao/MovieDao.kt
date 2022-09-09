package com.sally.tmbdreader.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sally.tmbdreader.db.entity.MovieEntity
import com.sally.tmbdreader.db.entity.MovieWithFavorite

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT movie.*, EXISTS(SELECT * FROM movie WHERE movie.movie_id == favorite.movie_id) AS is_favorite FROM movie LEFT JOIN favorite on movie.movie_id ==  favorite.movie_id")
    fun getMoviesWithFavorite(): LiveData<List<MovieWithFavorite>>

    @Query("SELECT movie.*, EXISTS(SELECT * FROM movie WHERE movie.movie_id == favorite.movie_id) AS is_favorite FROM movie LEFT JOIN favorite on movie.movie_id ==  favorite.movie_id WHERE movie.movie_id = :id")
    fun getMovieWithFavorite(id: Int): LiveData<MovieWithFavorite>

    @Delete
    fun delete(movie: MovieEntity)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()
}