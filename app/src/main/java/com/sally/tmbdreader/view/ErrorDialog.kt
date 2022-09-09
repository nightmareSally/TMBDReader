package com.sally.tmbdreader.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.sally.tmbdreader.databinding.DialogErrorBinding

class ErrorDialog(context: Context, private val message: String) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogErrorBinding.inflate(layoutInflater, null, false)
        binding.tvError.text = message

        binding.btnYes.setOnClickListener {
            dismiss()
        }

        setContentView(binding.root)
        setCancelable(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}