package com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals

import com.modarb.android.network.models.BaseResponse

data class DailyGoalsResponse(
    val `data`: Data,
) : BaseResponse()