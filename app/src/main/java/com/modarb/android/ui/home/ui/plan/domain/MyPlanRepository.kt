package com.modarb.android.ui.home.ui.plan.domain

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse

interface MyPlanRepository {

    suspend fun getMyPlanPage(workoutId: String, token: String): ApiResult<PlanPageResponse>
    suspend fun getCustomWorkouts(token: String): ApiResult<CustomWorkoutResponse>
}