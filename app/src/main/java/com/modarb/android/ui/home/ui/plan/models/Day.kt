package com.modarb.android.ui.home.ui.plan.models

data class Day(
    val day_number: Int,
    val day_type: String,
    val exercises: List<Exercise>,
    val is_done: Boolean,
    val total_number_exercises: Int
)