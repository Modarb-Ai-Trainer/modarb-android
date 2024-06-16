package com.modarb.android.ui.home.ui.plan.domain.models

import android.os.CountDownTimer

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
    var reps: Int,
    val sets: Int,
    val targetMuscles: TargetMuscles,
    var _currentSetCount: Int = 0,
    var _isDone: Boolean,
    var countDownTimer: CountDownTimer?,
    var isStarted: Boolean = false,
    var remainingTime: Long = duration * 1000L,
    var isTimeExerciseDone: Boolean = false,
    var isStartedSound: Boolean = false,
    var isStartedHelpSound: Boolean = false,
)