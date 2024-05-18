package com.modarb.android.ui.workout.data

import com.modarb.android.network.ApiService
import com.modarb.android.network.Result
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.workout.domain.repo.WorkoutRepository

class WorkoutRepositoryImpl(private val apiService: ApiService) : WorkoutRepository {

    override suspend fun markDoneWorkout(
        myWorkoutId: String, week: Int, day: Int, token: String
    ): Result<BaseResponse> {
        return try {
            val response = apiService.markDoneWorkout(myWorkoutId, week, day, token)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Failure(Throwable("Response body is null"))
            } else {
                Result.Failure(Throwable(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
