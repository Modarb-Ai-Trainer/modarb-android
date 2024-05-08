package com.modarb.android.ui.home.ui.home.models

data class MyWorkout(
    val id: String,
    val is_active: Boolean,
    val weeks: List<Week>,
    val workout: Workout
)