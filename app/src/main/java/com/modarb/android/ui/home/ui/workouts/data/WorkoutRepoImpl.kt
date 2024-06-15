package com.modarb.android.ui.home.ui.workouts.data

import com.google.gson.Gson
import com.modarb.android.network.ApiResult
import com.modarb.android.network.ApiService
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.ui.workouts.domain.WorkoutsRepo
import com.modarb.android.ui.home.ui.workouts.models.Workout
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse

class WorkoutRepoImpl(private val apiService: ApiService) : WorkoutsRepo {

    override suspend fun getWorkoutsPrograms(
        token: String
    ): ApiResult<WorkoutProgramsResponse> {

        return try {
            val response = apiService.getWorkoutPrograms(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError =
                    Gson().fromJson(errorResponse, WorkoutProgramsResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun enrollWorkoutProgram(
        token: String,
        workoutId: Workout
    ): ApiResult<BaseResponse> {
        return try {
            val response = apiService.enrollWorkoutProgram(token, workoutId)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError =
                    Gson().fromJson(errorResponse, BaseResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }


}
