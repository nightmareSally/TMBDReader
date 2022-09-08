package com.sally.tmbdreader.model

import com.google.gson.annotations.SerializedName
import com.sally.tmbdreader.db.entity.MovieEntity

data class MovieData (
    val results: List<MovieEntity>?,
    @SerializedName("total_pages")
    val totalPage: Int,
    val page: Int
)