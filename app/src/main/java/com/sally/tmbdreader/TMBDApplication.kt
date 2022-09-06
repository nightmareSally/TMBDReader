package com.sally.tmbdreader

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TMBDApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TMBDApplication)
            modules(listOf(apiModule, repositoryModule, viewModelModule))
        }
    }
}