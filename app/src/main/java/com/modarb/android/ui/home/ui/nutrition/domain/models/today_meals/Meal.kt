package com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals

data class Meal(
    val calories: Double,
    val carbs: Double,
    val created_at: String,
    val fats: Double,
    val id: String,
    val image: String,
    val ingredients: List<Ingredient>,
    val name: String,
    val proteins: Double,
    val type: String
)