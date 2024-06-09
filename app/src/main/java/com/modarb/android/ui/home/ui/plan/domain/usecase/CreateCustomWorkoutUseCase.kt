package com.modarb.android.ui.home.ui.plan.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.CreateCustomWorkoutRequest
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.create.CreateCustomWorkoutResponse

class CreateCustomWorkoutUseCase(private var planRepository: MyPlanRepository) {
    suspend fun invoke(
        token: String, createCustomWorkoutRequest: CreateCustomWorkoutRequest
    ): ApiResult<CreateCustomWorkoutResponse> {
        return planRepository.createCustomWorkout(token, createCustomWorkoutRequest)
    }
}