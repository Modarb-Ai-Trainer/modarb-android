package com.modarb.android.ui.home.ui.nutrition.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.modarb.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse

class GetAllIngredientsUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String, page: Int, limit: Int
    ): ApiResult<IngredientsResponse> {
        return nutritionRepository.getAllIngredients(token, page, limit)
    }
}