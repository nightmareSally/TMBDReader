package com.sally.tmbdreader.api

import com.sally.tmbdreader.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class DefaultQueryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val newUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }
}