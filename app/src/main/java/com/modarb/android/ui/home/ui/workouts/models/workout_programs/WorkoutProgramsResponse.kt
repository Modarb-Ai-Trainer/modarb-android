package com.modarb.android.ui.home.ui.workouts.models.workout_programs

import com.modarb.android.network.models.BaseResponse

data class WorkoutProgramsResponse(
    val `data`: List<Data>,
    val meta: Meta,
) : BaseResponse()