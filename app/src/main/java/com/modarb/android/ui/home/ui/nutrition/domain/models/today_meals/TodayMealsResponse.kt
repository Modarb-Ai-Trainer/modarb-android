package com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals

import com.modarb.android.network.models.BaseResponse

data class TodayMealsResponse(
    val `data`: Data,

    ) : BaseResponse()