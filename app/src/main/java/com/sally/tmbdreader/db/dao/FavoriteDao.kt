package com.sally.tmbdreader.db.dao

import androidx.room.*
import com.sally.tmbdreader.db.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(favorites: List<FavoriteEntity>)

    @Query("SELECT COUNT(*) FROM favorite WHERE movie_id = :id")
    fun getFavoriteCount(id: Int): Int

    @Delete
    fun delete(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite")
    suspend fun deleteAll()
}