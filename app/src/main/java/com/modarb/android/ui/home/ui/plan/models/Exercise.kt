package com.modarb.android.ui.home.ui.plan.models

data class Exercise(
    val __v: Int,
    val _id: String,
    val benefits: String,
    val category: String,
    val duration: Int,
    val equipments: List<Equipment>,
    val expectedDurationRange: ExpectedDurationRange,
    val instructions: String,
    val media: Media,
    val name: String,
    val reps: Int,
    val sets: Int,
    val targetMuscles: TargetMuscles
)