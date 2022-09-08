package com.sally.tmbdreader.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun showApiError(code: Int, message: String?) {
        val error = message ?: "Unknown error"
        Toast.makeText(this, "$error\nError code: $code", Toast.LENGTH_SHORT).show()
    }

    fun showNetworkError(message: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}