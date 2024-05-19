package com.modarb.android.ui.home.ui.plan.data

import com.modarb.android.network.ApiResult
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse

class PlanRepositoryImp(private val apiService: ApiService) : MyPlanRepository {

    override suspend fun getMyPlanPage(
        workoutId: String, token: String
    ): ApiResult<PlanPageResponse> {

        return try {
            val response = apiService.getPlanPage(workoutId, token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                ApiResult.Error(response.body() as PlanPageResponse)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getCustomWorkouts(token: String): ApiResult<CustomWorkoutResponse> {
        return try {
            val response = apiService.getCustomWorkouts(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                ApiResult.Error(response.body() as CustomWorkoutResponse)

            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }


}
