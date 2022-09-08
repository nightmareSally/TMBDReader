package com.sally.tmbdreader.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sally.tmbdreader.db.entity.MovieWithFavorite

class DiffCallback: DiffUtil.ItemCallback<MovieWithFavorite>() {
    override fun areItemsTheSame(oldItem: MovieWithFavorite, newItem: MovieWithFavorite): Boolean {
        return oldItem.movieInfo.id == newItem.movieInfo.id
    }

    override fun areContentsTheSame(oldItem: MovieWithFavorite, newItem: MovieWithFavorite): Boolean {
        return oldItem.movieInfo.imageUrl == newItem.movieInfo.imageUrl
                && oldItem.movieInfo.title == newItem.movieInfo.title
                && oldItem.isFavorite == newItem.isFavorite
    }
}