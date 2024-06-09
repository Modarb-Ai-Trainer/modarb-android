package com.modarb.android.ui.home.ui.plan.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.plan.domain.MyPlanRepository
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse

class SearchExercisesUseCase(private val planRepository: MyPlanRepository) {
    suspend fun invoke(
        token: String, searchTerm: String, filter: String
    ): ApiResult<ExercisesResponse> {
        return planRepository.getSearchExercises(token, searchTerm, filter)
    }
}