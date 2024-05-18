package com.modarb.android.ui.home.ui.plan.domain.usecase

import com.modarb.android.network.Result
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse

class PlanPageUseCase(private val planRepository: MyPlanRepository) {
    suspend fun invoke(workoutId: String, token: String): Result<PlanPageResponse> {
        return planRepository.getMyPlanPage(workoutId, token)
    }
}