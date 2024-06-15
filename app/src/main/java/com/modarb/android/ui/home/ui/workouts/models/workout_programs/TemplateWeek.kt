package com.modarb.android.ui.home.ui.workouts.models.workout_programs

data class TemplateWeek(
    val days: List<Day>,
    val week_description: String,
    val week_name: String,
    val week_number: Int
)