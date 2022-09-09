package com.sally.tmbdreader.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
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
            itemAnimator = null
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (!recyclerView.canScrollVertically(1)
                            && recyclerView.canScrollVertically(-1)
                        ) {
                            mViewModel.fetchMovies()
                        }
                    }
                }
            })
        }

        mViewModel.movies.observe(this) {
            movieListAdapter.submitList(it)
        }

        mViewModel.onLogoutSuccess.observe(this) {
            startActivity(LoginActivity.getIntent(this))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            mViewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }
}