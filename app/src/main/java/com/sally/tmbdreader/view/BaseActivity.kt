package com.sally.tmbdreader.view

import android.app.Dialog
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sally.tmbdreader.R
import com.sally.tmbdreader.databinding.DialogErrorBinding

open class BaseActivity : AppCompatActivity() {

    private var errorDialog: Dialog? = null

    fun showApiError(code: Int, message: String?) {
        if (errorDialog?.isShowing == true) {
            return
        }
        val error = message ?: "Unknown error"
        errorDialog = ErrorDialog(this, getString(R.string.error_api).format(code, error))
        errorDialog?.show()
//        errorDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun showNetworkError(message: String) {
        if (errorDialog?.isShowing == true) {
            return
        }

        errorDialog = ErrorDialog(this, getString(R.string.error_network).format(message))
        errorDialog?.show()
//        errorDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}