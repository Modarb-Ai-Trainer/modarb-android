package com.modarb.android.ui.home.ui.nutrition.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse

class GetTodayMealUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String
    ): ApiResult<TodayMealsResponse> {
        return nutritionRepository.getTodayMeal(token)
    }
}