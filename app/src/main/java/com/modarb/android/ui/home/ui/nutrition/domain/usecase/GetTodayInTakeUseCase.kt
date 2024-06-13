package com.modarb.android.ui.home.ui.nutrition.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse

class GetTodayInTakeUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String
    ): ApiResult<TodayInTakeResponse> {
        return nutritionRepository.getTodayInTake(token)
    }
}