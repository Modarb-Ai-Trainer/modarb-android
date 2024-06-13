package com.modarb.android.ui.home.ui.nutrition.domain.models

data class MealPlan(
    val description: String,
    val duration: Int,
    val id: String,
    val image: String,
    val key_features: List<KeyFeature>,
    val level: String,
    val your_journey: String
)