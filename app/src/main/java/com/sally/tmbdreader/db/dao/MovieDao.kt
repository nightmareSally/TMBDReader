package com.sally.tmbdreader.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sally.tmbdreader.db.entity.MovieEntity
import com.sally.tmbdreader.db.entity.MovieWithFavorite

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Insert
    fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT *, EXISTS(SELECT * FROM movie WHERE id == favorite.movie_id) FROM movie LEFT JOIN favorite on movie.id ==  favorite.movie_id")
    fun getMoviesWithFavorite(): LiveData<List<MovieWithFavorite>>

    @Delete
    fun delete(movie: MovieEntity)

    @Query("DELETE FROM movie")
    fun deleteAll()
}