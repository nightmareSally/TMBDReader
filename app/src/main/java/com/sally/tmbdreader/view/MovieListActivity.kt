package com.sally.tmbdreader.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sally.tmbdreader.R
import com.sally.tmbdreader.adapter.MarginItemDecoration
import com.sally.tmbdreader.adapter.MovieListAdapter
import com.sally.tmbdreader.databinding.ActivityMovieListBinding
import com.sally.tmbdreader.viewModel.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, MovieListActivity::class.java)
    }

    private lateinit var mBinding: ActivityMovieListBinding
    private val mViewModel: MovieListViewModel by viewModel()
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        mBinding.lifecycleOwner = this
        movieListAdapter = MovieListAdapter(mViewModel) {
            startActivity(MovieDetailActivity.getIntent(this, it))
        }
        mBinding.rvMovieList.apply {
            adapter = movieListAdapter
            addItemDecoration(MarginItemDecoration(10f))
        }

        mViewModel.movies.observe(this) {
            movieListAdapter.submitList(it)
        }
    }
}