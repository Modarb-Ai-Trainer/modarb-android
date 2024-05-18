package com.modarb.android.ui.workout.domain.models

import com.modarb.android.network.models.BaseResponse

data class WorkoutsResponse(
    val `data`: Data
) : BaseResponse()