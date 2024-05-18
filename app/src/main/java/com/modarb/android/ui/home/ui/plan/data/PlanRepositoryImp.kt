package com.modarb.android.ui.home.ui.plan.data

import android.content.Context
import com.modarb.android.network.ApiResult
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import retrofit2.Response

class PlanRepositoryImp(private val apiService: ApiService) : MyPlanRepository {


    // TODO handle get custom workouts
    suspend fun getCustomWorkouts(context: Context): Response<PlanPageResponse> {
        return apiService.getCustomWorkouts(
            "Bearer " + UserPrefUtil.getUserData(context)!!.token
        )
    }

    override suspend fun getMyPlanPage(
        workoutId: String,
        token: String
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


}
