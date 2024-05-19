package com.modarb.android.ui.home.ui.plan.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse

class GetCustomWorkoutUseCase(private var planRepository: MyPlanRepository) {
    suspend fun invoke(token: String): ApiResult<CustomWorkoutResponse> {
        return planRepository.getCustomWorkouts(token)
    }
}