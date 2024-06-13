package com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals

data class Data(
    val days: List<Day>,
    val id: String,
    val isActive: Boolean,
    val meal_plan: MealPlan,
    val user: String
)