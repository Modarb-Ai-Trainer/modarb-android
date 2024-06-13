package com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan

data class Ingredient(
    val calories: Int,
    val carbs: Int,
    val fats: Int,
    val id: String,
    val name: String,
    val proteins: Int,
    val serving_size: Int,
    val serving_size_unit: String,
    val servings_count: Int,
    val servings_count_unit: String
)