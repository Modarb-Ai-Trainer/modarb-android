package com.modarb.android.ui.home.ui.plan.domain.models

data class CreateCustomWorkoutRequest(
    val exercises: List<String>,
    val name: String,
    val user: String
)