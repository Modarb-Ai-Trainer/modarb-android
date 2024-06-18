package com.modarb.android.ui.home.ui.nutrition.domain.usecase

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.modarb.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse

class SearchIngredientsUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String, searchTerm: String
    ): ApiResult<IngredientsResponse> {
        return nutritionRepository.searchIngredients(token, searchTerm)
    }
}