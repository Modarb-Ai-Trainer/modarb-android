package com.modarb.android.ui.home.ui.home.models

data class Data(
    val id: String,
    val is_active: Boolean,
    val user: User,
    val weeks: List<Week>,
    val workout: Workout
)