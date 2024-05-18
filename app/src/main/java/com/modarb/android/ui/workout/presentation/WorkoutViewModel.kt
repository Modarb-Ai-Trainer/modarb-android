package com.modarb.android.ui.workout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.network.Result
import com.modarb.android.network.RetrofitService
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.workout.data.WorkoutRepositoryImpl
import com.modarb.android.ui.workout.domain.usecase.MarkWorkoutDoneUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {

    private val apiService = RetrofitService.createService()
    private val workoutRepository = WorkoutRepositoryImpl(apiService)
    private val markWorkoutDoneUseCase = MarkWorkoutDoneUseCase(workoutRepository)

    private val _workoutStatus = MutableStateFlow<Result<BaseResponse>?>(null)
    val workoutStatus: StateFlow<Result<BaseResponse>?> get() = _workoutStatus

    fun markWorkoutDone(myWorkoutId: String, week: Int, day: Int, token: String) {
        viewModelScope.launch {
            val result = markWorkoutDoneUseCase(myWorkoutId, week, day, token)
            _workoutStatus.value = result
        }
    }
}
