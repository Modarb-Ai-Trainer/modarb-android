package com.modarb.android.network


import retrofit2.Response

abstract class BaseRetrofitService {

    sealed class ApiResponse<out T : Any> {
        data class Success<out T : Any>(val data: T) : ApiResponse<T>()
        data class Error(val message: String? = null) : ApiResponse<Nothing>()
    }

    suspend fun <T : Any> handleApiCall(call: suspend () -> Response<T>): ApiResponse<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                ApiResponse.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                ApiResponse.Error(errorBody ?: "Unknown error")
            }
        } catch (e: Exception) {
            ApiResponse.Error(e.message)
        }
    }


}
