package com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals

data class Day(
    val day_number: Int,
    val is_eaten: Boolean,
    val meals: List<Meal>
)