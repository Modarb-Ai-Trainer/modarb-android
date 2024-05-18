package com.modarb.android.ui.workout.domain.models

data class Day(
    val day_number: Int,
    val day_type: String,
    val exercises: List<Exercise>,
    val total_number_exercises: Int
)