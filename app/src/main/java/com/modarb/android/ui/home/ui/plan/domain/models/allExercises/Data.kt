package com.modarb.android.ui.home.ui.plan.domain.models.allExercises

data class Data(
    val benefits: String,
    val category: String,
    val coverImage: String,
    val duration: Int,
    val equipments: List<Equipment>,
    val exerciseType: String,
    val expectedDurationRange: ExpectedDurationRange,
    val id: String,
    val instructions: String,
    val media: Media,
    val name: String,
    val reps: Int,
    val sets: Int,
    val targetMuscles: TargetMuscles
)