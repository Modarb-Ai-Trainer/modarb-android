package com.modarb.android.ui.workout.repo

import com.modarb.android.network.Result
import com.modarb.android.network.models.BaseResponse

class MarkWorkoutDoneUseCase(private val workoutRepository: WorkoutRepository) {

    suspend operator fun invoke(
        myWorkoutId: String, week: Int, day: Int, token: String
    ): Result<BaseResponse> {
        return workoutRepository.markDoneWorkout(myWorkoutId, week, day, token)
    }
}
