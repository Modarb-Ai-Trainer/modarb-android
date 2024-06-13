package com.modarb.android.ui.home.ui.nutrition.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse

class GetMyMealPlanUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String
    ): ApiResult<MyMealPlanResponse> {
        return nutritionRepository.getMyMealPlan(token)
    }
}