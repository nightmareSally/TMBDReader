package com.sally.tmbdreader.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sally.tmbdreader.db.dao.FavoriteDao
import com.sally.tmbdreader.db.dao.MovieDao
import com.sally.tmbdreader.db.entity.FavoriteEntity
import com.sally.tmbdreader.db.entity.MovieEntity

@Database(entities = [MovieEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class TMBDDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getFavoriteDao(): FavoriteDao
}