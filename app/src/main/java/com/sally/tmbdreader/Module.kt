package com.sally.tmbdreader

import com.sally.tmbdreader.api.ApiErrorInterceptor
import com.sally.tmbdreader.api.ResultCallAdapterFactory
import com.sally.tmbdreader.api.service.AccountService
import com.sally.tmbdreader.api.service.MovieService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {

}

val repositoryModule = module {

}

val apiModule = module {
    single { getRetrofit() }
    single { get<Retrofit>().create(AccountService::class.java) }
    single { get<Retrofit>().create(MovieService::class.java) }
}

fun getRetrofit(): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(ApiErrorInterceptor())
        .build()
    return Retrofit.Builder()
        .baseUrl(BuildConfig.Host_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .client(okHttpClient)
        .build()
}