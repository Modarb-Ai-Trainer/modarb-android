package com.modarb.android.ui.home.ui.nutrition.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse

class GetAllMyMealsPlansUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String
    ): ApiResult<AllMealsPlansResponse> {
        return nutritionRepository.getAllMealsPlans(token)
    }
}