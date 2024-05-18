package com.modarb.android.ui.workout.domain.models

data class Exercise(
    val _id: String,
    val media: Media,
    val name: String,
    val reps: Int,
    val sets: Int
)