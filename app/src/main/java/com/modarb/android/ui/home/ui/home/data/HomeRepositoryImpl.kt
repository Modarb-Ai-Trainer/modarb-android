package com.modarb.android.ui.home.ui.home.data

import com.modarb.android.network.ApiService
import com.modarb.android.network.Result
import com.modarb.android.ui.home.ui.home.domain.HomeRepository
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse

class HomeRepositoryImpl(private val apiService: ApiService) : HomeRepository {
    override suspend fun getHomePage(token: String): Result<HomePageResponse> {

        return try {
            val response = apiService.getHomePage(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Failure(Throwable("Response body is null"))
            } else {
                Result.Failure(Throwable(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (E: Exception) {
            Result.Failure(E)
        }
    }


}
