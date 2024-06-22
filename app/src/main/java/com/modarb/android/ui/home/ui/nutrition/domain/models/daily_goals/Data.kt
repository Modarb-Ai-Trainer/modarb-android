package com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals

data class Data(
    val exercisesCals: Double,
    val exercisesHours: Double,
    val sleepDone: Int,
    val sleepGoal: Int,
    val stepsDone: Int,
    val stepsGoal: Int,
    val waterConsumed: Int,
    val waterGoal: Int
)