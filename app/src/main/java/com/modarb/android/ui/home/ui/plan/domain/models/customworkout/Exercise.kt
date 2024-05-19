package com.modarb.android.ui.home.ui.plan.domain.models.customworkout

data class Exercise(
    val __v: Int,
    val _id: String,
    val benefits: String,
    val category: String,
    val coverImage: String,
    val duration: Int,
    val equipments: List<String>,
    val exerciseType: String,
    val expectedDurationRange: ExpectedDurationRange,
    val instructions: String,
    val media: Media,
    var reps: Int,
    val sets: Int,
    val name: String,
    val targetMuscles: TargetMuscles
)