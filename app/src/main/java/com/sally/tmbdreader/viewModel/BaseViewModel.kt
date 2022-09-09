package com.sally.tmbdreader.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val apiError = MutableLiveData<Pair<Int, String?>>()
    val networkError = MutableLiveData<String>()

}