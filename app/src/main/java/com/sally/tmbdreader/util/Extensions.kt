package com.sally.tmbdreader.util

import android.content.res.Resources
import com.sally.tmbdreader.api.ApiResult

object Extensions {
    suspend fun <T : Any> ApiResult<T>.onSuccess(
            executable: suspend (T) -> Unit
    ): ApiResult<T> = apply {
        if (this is ApiResult.Success) {
            executable(data)
        }
    }

    suspend fun <T : Any> ApiResult<T>.onApiError(
            executable: suspend (code: Int, message: String) -> Unit
    ): ApiResult<T> = apply {
        if (this is ApiResult.ApiError) {
            executable(code, message)
        }
    }

    suspend fun <T : Any> ApiResult<T>.onNetworkError(
            executable: suspend (message: String) -> Unit
    ): ApiResult<T> = apply {
        if (this is ApiResult.NetworkError) {
            executable(message)
        }
    }

    fun Float.dpToPx() = this * Resources.getSystem().displayMetrics.density
}