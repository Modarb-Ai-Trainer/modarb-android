package com.modarb.android.ui.onboarding.models

data class Preferences(
    val fitness_goal: String,
    val preferred_days: List<String>,
    val preferred_equipment: List<String>,
    val target_weight: Int,
    val workout_frequency: Int,
    val workout_place: String
)