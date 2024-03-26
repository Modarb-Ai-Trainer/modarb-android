package com.modarb.android.network

import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private const val BASE_URL = "https://modarb-backend.onrender.com/"
    inline fun <reified T> handleRequest(
        response: Response<T>,
        onSuccess: (T) -> Unit,
        onError: (T?) -> Unit
    ) {
        if (response.isSuccessful) {
            response.body()?.let {
                onSuccess(it)
            } ?: onError(null)
        } else {
            val errorBody = response.errorBody()?.string()
            val errorData: T? = errorBody?.let { parseErrorBody(it) }
            onError(errorData)
        }
    }

    inline fun <reified T> parseErrorBody(errorBody: String): T? {
        return try {
            Gson().fromJson(errorBody, T::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun createService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
