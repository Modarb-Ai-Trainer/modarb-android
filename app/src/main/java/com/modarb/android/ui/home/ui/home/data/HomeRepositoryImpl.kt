package com.modarb.android.ui.home.ui.home.data

import com.google.gson.Gson
import com.modarb.android.network.ApiResult
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.home.domain.HomeRepository
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse

class HomeRepositoryImpl(private val apiService: ApiService) : HomeRepository {
    override suspend fun getHomePage(token: String): ApiResult<HomePageResponse> {

        return try {
            val response = apiService.getHomePage(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, HomePageResponse::class.java)
                ApiResult.Error(parsedError)

            }
        } catch (E: Exception) {
            ApiResult.Failure(E)
        }
    }

    override suspend fun getTodayInTake(token: String): ApiResult<TodayInTakeResponse> {
        return try {
            val response = apiService.getHomePageTodayInTake(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, TodayInTakeResponse::class.java)
                ApiResult.Error(parsedError)

            }
        } catch (E: Exception) {
            ApiResult.Failure(E)
        }
    }


}
