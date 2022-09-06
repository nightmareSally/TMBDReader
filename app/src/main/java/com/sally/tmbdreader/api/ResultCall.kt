package com.sally.tmbdreader.api

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(private val delegate: Call<T>) :
    Call<ApiResult<T>> {

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(ApiResult.Success(response.body()!!))
                    )
                } else {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            ApiResult.NetworkError(response.message())
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (t is TMBDError) {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(ApiResult.ApiError(t.code, t.message ?: "Unknown Error"))
                    )
                    return
                }
                val errorMessage = when (t) {
                    is IOException -> "No internet connection"
                    is HttpException -> "Something went wrong"
                    else -> t.localizedMessage
                }
                callback.onResponse(
                    this@ResultCall,
                    Response.success(ApiResult.NetworkError(errorMessage))
                )
            }
        })
    }

    override fun clone(): Call<ApiResult<T>> {
        return ResultCall(delegate.clone())
    }

    override fun execute(): Response<ApiResult<T>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}