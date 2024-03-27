package com.modarb.android.ui.onboarding.models.RequestModels

data class Preferences(
    var fitness_goal: String,
    var preferred_equipment: List<String>,
    var target_weight: Int,
    var workout_place: String
)