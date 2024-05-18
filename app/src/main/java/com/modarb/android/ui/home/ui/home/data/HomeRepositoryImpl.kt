package com.modarb.android.ui.home.ui.home.data

import com.modarb.android.network.ApiResult
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.home.domain.HomeRepository
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse

class HomeRepositoryImpl(private val apiService: ApiService) : HomeRepository {
    override suspend fun getHomePage(token: String): ApiResult<HomePageResponse> {

        return try {
            val response = apiService.getHomePage(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                ApiResult.Failure(Throwable(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (E: Exception) {
            ApiResult.Failure(E)
        }
    }


}
