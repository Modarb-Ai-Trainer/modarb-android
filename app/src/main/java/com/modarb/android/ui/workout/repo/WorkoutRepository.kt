package com.modarb.android.ui.workout.repo

import com.modarb.android.network.Result
import com.modarb.android.network.models.BaseResponse

interface WorkoutRepository {
    suspend fun markDoneWorkout(
        myWorkoutId: String,
        week: Int,
        day: Int,
        token: String
    ): Result<BaseResponse>
}
