package com.modarb.android.ui.home.ui.nutrition.models

import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.Meal

data class NutritionDataModel(
    var dayName: String,
    var meals: List<Meal>
)
