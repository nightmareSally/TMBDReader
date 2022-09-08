package com.sally.tmbdreader.db.entity

import androidx.room.ColumnInfo

data class MovieWithFavorite(
        @ColumnInfo(name = "movie_info")
        val movieInfo: MovieEntity,
        @ColumnInfo(name = "is_favorite")
        val isFavorite: Boolean
)