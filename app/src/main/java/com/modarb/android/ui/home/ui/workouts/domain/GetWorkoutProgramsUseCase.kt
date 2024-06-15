package com.modarb.android.ui.home.ui.workouts.domain

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse

class GetWorkoutProgramsUseCase(private var workoutsProgramsRepo: WorkoutsRepo) {
    suspend fun invoke(token: String): ApiResult<WorkoutProgramsResponse> {
        return workoutsProgramsRepo.getWorkoutsPrograms(token)
    }
}