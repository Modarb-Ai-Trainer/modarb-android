package com.modarb.android.ui.home.ui.plan.logic

import android.content.Context
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.models.PlanPageResponse
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import retrofit2.Response

class PlanRepository(private val apiService: ApiService) {

    suspend fun getPlanPage(context: Context): Response<PlanPageResponse> {
        return apiService.getPlanPage(
            WorkoutData.workoutId,
            "Bearer " + UserPrefUtil.getUserData(context)!!.token
        )
    }


}
