package com.sally.tmbdreader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sally.tmbdreader.BuildConfig
import com.sally.tmbdreader.databinding.ItemMovieBinding
import com.sally.tmbdreader.db.entity.MovieWithFavorite
import com.sally.tmbdreader.viewModel.MovieListViewModel

class MovieListAdapter(
    private val viewModel: MovieListViewModel,
    private val onViewClicked: (Int) -> Unit
) :
    ListAdapter<MovieWithFavorite, MovieListAdapter.MovieListViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieListViewHolder(viewModel, binding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener {
            onViewClicked.invoke(getItem(position).movieInfo.movieId)
        }
    }

    class MovieListViewHolder(
        val viewModel: MovieListViewModel,
        private val binding: ItemMovieBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: MovieWithFavorite) {
            binding.movie = movie
            binding.ivFavorite.setOnClickListener {
                viewModel.setFavorite(movie.movieInfo.movieId)
            }
            binding.executePendingBindings()

            Glide.with(binding.root)
                .load(BuildConfig.Image_URL + movie.movieInfo.imageUrl)
                .into(binding.ivPoster)
        }
    }
}