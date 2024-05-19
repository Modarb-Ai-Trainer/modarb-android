package com.modarb.android.ui.home.ui.plan.domain.models.customworkout

data class CustomWorkoutResponse(
    val `data`: List<Data>,
    val message: String,
    val meta: Meta,
    val status: Int
)