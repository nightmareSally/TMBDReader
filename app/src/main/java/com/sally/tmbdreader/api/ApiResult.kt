package com.sally.tmbdreader.api

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T): ApiResult<T>()
    data class ApiError(val code: Int, val message: String): ApiResult<Nothing>()
    data class NetworkError(val message: String): ApiResult<Nothing>()
}