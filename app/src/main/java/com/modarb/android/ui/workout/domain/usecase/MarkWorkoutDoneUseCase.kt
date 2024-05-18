package com.modarb.android.ui.workout.domain.usecase

import com.modarb.android.network.Result
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.workout.domain.repo.WorkoutRepository

class MarkWorkoutDoneUseCase(private val workoutRepository: WorkoutRepository) {

    suspend operator fun invoke(
        myWorkoutId: String, week: Int, day: Int, token: String
    ): Result<BaseResponse> {
        return workoutRepository.markDoneWorkout(myWorkoutId, week, day, token)
    }
}
