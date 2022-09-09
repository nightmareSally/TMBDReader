package com.sally.tmbdreader

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.sally.tmbdreader.api.ApiErrorInterceptor
import com.sally.tmbdreader.api.DefaultQueryInterceptor
import com.sally.tmbdreader.api.ResultCallAdapterFactory
import com.sally.tmbdreader.api.service.AccountService
import com.sally.tmbdreader.api.service.MovieService
import com.sally.tmbdreader.db.TMBDDatabase
import com.sally.tmbdreader.repository.AccountRepository
import com.sally.tmbdreader.repository.MovieRepository
import com.sally.tmbdreader.util.SharedPreference
import com.sally.tmbdreader.viewModel.LoginViewModel
import com.sally.tmbdreader.viewModel.MovieDetailViewModel
import com.sally.tmbdreader.viewModel.MovieListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

const val DATABASE_NAME = "TMBD_DATABASE"

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get(), androidApplication()) }
    viewModel { MovieListViewModel(get(), get(), get(), androidApplication()) }
    viewModel { MovieDetailViewModel(get(), get(), get(), get(), androidApplication()) }
}

val apiModule = module {
    single { getRetrofit() }
    single { get<Retrofit>().create(AccountService::class.java) }
    single { get<Retrofit>().create(MovieService::class.java) }
}

val dbModule = module {
    single {
        Room.databaseBuilder(androidApplication(), TMBDDatabase::class.java, DATABASE_NAME).build()
    }
    single { get<TMBDDatabase>().getMovieDao() }
    single { get<TMBDDatabase>().getFavoriteDao() }
}

val repositoryModule = module {
    single { getSharedPreferences(androidContext()) }
    single { SharedPreference(get()) }
    single { AccountRepository(get(), get()) }
    single { MovieRepository(get(), get()) }
}


fun getRetrofit(): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
        )
        .addInterceptor(DefaultQueryInterceptor())
        .addInterceptor(ApiErrorInterceptor())
        .build()
    return Retrofit.Builder()
        .baseUrl(BuildConfig.Host_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .client(okHttpClient)
        .build()
}

fun getSharedPreferences(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
}