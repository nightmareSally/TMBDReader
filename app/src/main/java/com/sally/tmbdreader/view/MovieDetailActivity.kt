package com.sally.tmbdreader.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sally.tmbdreader.R
import com.sally.tmbdreader.databinding.ActivityMovieDetailBinding
import com.sally.tmbdreader.viewModel.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailActivity : BaseActivity() {
    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        fun getIntent(context: Context, id: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ID, id)
            }
        }
    }

    private lateinit var mBinding: ActivityMovieDetailBinding
    private val mViewModel: MovieDetailViewModel by viewModel {
        parametersOf(intent.getIntExtra(MOVIE_ID, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
    }
}