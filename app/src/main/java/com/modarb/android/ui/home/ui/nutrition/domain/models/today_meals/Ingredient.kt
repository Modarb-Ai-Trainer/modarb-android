package com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals

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