package com.sally.tmbdreader.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sally.tmbdreader.db.entity.FavoriteEntity.Companion.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
data class FavoriteEntity(
        @PrimaryKey
        @ColumnInfo(name = "movie_id")
        val id: Int
) {
    companion object {
        const val FAVORITE_TABLE = "favorite"
    }
}