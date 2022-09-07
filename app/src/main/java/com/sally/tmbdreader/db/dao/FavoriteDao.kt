package com.sally.tmbdreader.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.sally.tmbdreader.db.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntity)

    @Insert
    fun insertAll(favorites: List<FavoriteEntity>)

    @Delete
    fun delete(favorite: FavoriteEntity)
}