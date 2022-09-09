package com.sally.tmbdreader.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sally.tmbdreader.db.entity.MovieEntity.Companion.MOVIE_TABLE

@Entity(tableName = MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val mainId: Int = 0,
    @SerializedName("id")
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val imageUrl: String? = "",
    val title: String = "",
    val overview: String = "",
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String = ""
) {
    companion object {
        const val MOVIE_TABLE = "movie"
    }
}