package com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals

data class Data(
    val exercisesCals: Int,
    val exercisesHours: Int,
    val sleepDone: Int,
    val sleepGoal: Int,
    val stepsDone: Int,
    val stepsGoal: Int,
    val waterConsumed: Int,
    val waterGoal: Int
)