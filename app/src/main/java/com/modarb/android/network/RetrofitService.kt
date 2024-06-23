package com.modarb.android.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {

    var BASE_URL = "https://moahmedwafy-modarb-be.hf.space/"

    fun changeBaseUrl(newUrl: String) {
        BASE_URL = newUrl
    }
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
//        if (!NetworkHelper.isEmulator()) {
//            BASE_URL = "http://192.168.1.9:4000/"
//        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)


        // Create an OkHttpClient and attach the logging interceptor
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        try {
            return Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiService::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

}
