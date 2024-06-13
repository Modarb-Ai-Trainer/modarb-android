package com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan

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