package com.modarb.android.ui.home.ui.plan.domain.models

data class Data(
    val id: String,
    val is_active: Boolean,
    val user: String,
    val weeks: List<Week>,
    val workout: Workout
)