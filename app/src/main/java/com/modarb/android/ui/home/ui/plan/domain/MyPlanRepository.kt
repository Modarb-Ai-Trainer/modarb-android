package com.modarb.android.ui.home.ui.plan.domain

import com.modarb.android.network.Result
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse

interface MyPlanRepository {

    suspend fun getMyPlanPage(workoutId: String, token: String): Result<PlanPageResponse>
}