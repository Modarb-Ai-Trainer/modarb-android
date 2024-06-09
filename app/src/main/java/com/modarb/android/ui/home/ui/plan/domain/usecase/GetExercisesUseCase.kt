package com.modarb.android.ui.home.ui.plan.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse

class GetExercisesUseCase(private val planRepository: MyPlanRepository) {
    suspend fun invoke(
        token: String, filterName: String, filterVal: String, page: Int, limit: Int
    ): ApiResult<ExercisesResponse> {
        return planRepository.getExercises(token, filterName, filterVal, page, limit)
    }
}