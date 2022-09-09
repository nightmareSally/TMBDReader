package com.sally.tmbdreader.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.sally.tmbdreader.R
import com.sally.tmbdreader.databinding.ActivityLoginBinding
import com.sally.tmbdreader.databinding.DialogLoadingBinding
import com.sally.tmbdreader.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    private lateinit var mBinding: ActivityLoginBinding
    private val mViewModel: LoginViewModel by viewModel()
    private var loadingDialog: Dialog? = null
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
            finish()
        }

        mViewModel.loading.observe(this) {
            if (it) {
                if (loadingDialog?.isShowing == true) {
                    return@observe
                }
                val binding = DialogLoadingBinding.inflate(layoutInflater, null, false)
                loadingDialog = AlertDialog.Builder(this)
                    .setView(binding.root)
                    .setCancelable(false)
                    .create()
                loadingDialog?.show()
                loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            } else {
                loadingDialog?.dismiss()
            }
        }
    }
}