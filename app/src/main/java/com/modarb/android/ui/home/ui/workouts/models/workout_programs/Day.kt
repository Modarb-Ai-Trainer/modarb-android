package com.modarb.android.ui.home.ui.workouts.models.workout_programs

data class Day(
    val day_number: Int,
    val day_type: String,
    val exercises: List<String>,
    val total_number_exercises: Int
)