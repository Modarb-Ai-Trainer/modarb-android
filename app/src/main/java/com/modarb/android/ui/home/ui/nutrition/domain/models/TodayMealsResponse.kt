package com.modarb.android.ui.home.ui.nutrition.domain.models

import com.modarb.android.network.models.BaseResponse

data class TodayMealsResponse(
    val `data`: Data,
) : BaseResponse()