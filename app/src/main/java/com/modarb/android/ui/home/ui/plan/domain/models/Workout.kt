package com.modarb.android.ui.home.ui.plan.domain.models

data class Workout(
    val created_by: String,
    val description: String,
    val fitness_goal: String,
    val fitness_level: String,
    val id: String,
    val image: String,
    val min_per_day: Int,
    val name: String,
    val place: List<String>,
    val total_number_days: Int,
    val type: String
)