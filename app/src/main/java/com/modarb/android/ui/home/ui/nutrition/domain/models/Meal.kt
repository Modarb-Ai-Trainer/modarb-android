package com.modarb.android.ui.home.ui.nutrition.domain.models

data class Meal(
    val calories: Double,
    val carbs: Double,
    val created_at: String,
    val fats: Double,
    val id: String,
    val ingredients: List<String>,
    val name: String,
    val proteins: Double,
    val type: String
)