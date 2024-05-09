package com.modarb.android.ui.workout.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WorkoutModelFactory(private val workoutRepository: WorkoutRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            return WorkoutViewModel(workoutRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
