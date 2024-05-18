package com.modarb.android.ui.home.ui.home.domain.models

import com.modarb.android.ui.home.ui.plan.domain.models.Week

data class MyWorkout(
    val id: String,
    val is_active: Boolean,
    val weeks: List<Week>,
    val workout: Workout
)