package com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan

data class AllMealsPlansResponse(
    val `data`: List<Data>,
    val message: String,
    val meta: Meta,
    val status: Int
)