package com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals

data class MealPlan(
    val __v: Int,
    val _id: String,
    val description: String,
    val duration: Int,
    val image: String,
    val key_features: List<KeyFeature>,
    val level: String,
    val your_journey: String
)