package com.modarb.android.ui.home.ui.nutrition.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse

class GetDailyGoalsUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String
    ): ApiResult<DailyGoalsResponse> {
        return nutritionRepository.getDailyGoals(token)
    }
}