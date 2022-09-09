package com.sally.tmbdreader.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class MovieWithFavorite(
        @Embedded
        val movieInfo: MovieEntity,
        @ColumnInfo(name = "is_favorite")
        val isFavorite: Boolean
)