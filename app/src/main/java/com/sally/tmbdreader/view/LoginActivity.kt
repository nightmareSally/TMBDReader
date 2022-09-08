package com.sally.tmbdreader.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sally.tmbdreader.R
import com.sally.tmbdreader.databinding.ActivityLoginBinding
import com.sally.tmbdreader.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    private val mViewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        mViewModel.apiError.observe(this) {
            showApiError(it.first, it.second)
        }

        mViewModel.networkError.observe(this) {
            showNetworkError(it)
        }

        mViewModel.onLoginSuccess.observe(this) {
            startActivity(MovieListActivity.getIntent(this))
        }
    }
}