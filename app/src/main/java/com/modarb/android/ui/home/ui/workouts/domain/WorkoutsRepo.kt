package com.modarb.android.ui.home.ui.workouts.domain

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse

interface WorkoutsRepo {

    suspend fun getWorkoutsPrograms(token: String): ApiResult<WorkoutProgramsResponse>


}