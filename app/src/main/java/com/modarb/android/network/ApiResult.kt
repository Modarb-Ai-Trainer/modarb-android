package com.modarb.android.network

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error<out T>(val data: T) : ApiResult<T>()

    data class Failure(val exception: Throwable) : ApiResult<Nothing>()
}
