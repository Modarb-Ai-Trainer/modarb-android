package com.modarb.android.network

import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private const val BASE_URL = "http://10.0.2.2:4000/"
    inline fun <reified T> handleRequest(
        response: Response<T>, onSuccess: (T) -> Unit, onError: (T?) -> Unit
    ) {
        if (response.isSuccessful) {
            try {
                response.body()?.let {
                    onSuccess(it)
                } ?: onError(null)
            } catch (e: Exception) {
                onError(null)
            }
        } else {
            try {
                val errorBody = response.errorBody()?.string()
                val errorData: T? = errorBody?.let { parseErrorBody(it) }
                onError(errorData)
            } catch (e: Exception) {
                onError(null)
            }
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
        try {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiService::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

}
