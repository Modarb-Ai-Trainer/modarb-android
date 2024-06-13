package com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan

data class Meal(
    val calories: Double,
    val carbs: Double,
    val created_at: String,
    val fats: Double,
    val id: String,
    val ingredients: List<Ingredient>,
    val name: String,
    val proteins: Double,
    val type: String
)