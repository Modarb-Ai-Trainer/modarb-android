package com.modarb.android.ui.home.ui.workouts.domain

import com.modarb.android.network.ApiResult
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.ui.workouts.models.Workout

class EnrollWorkoutProgramUseCase(private var workoutsProgramsRepo: WorkoutsRepo) {
    suspend fun invoke(token: String, workoutId: Workout): ApiResult<BaseResponse> {
        return workoutsProgramsRepo.enrollWorkoutProgram(token, workoutId)
    }
}