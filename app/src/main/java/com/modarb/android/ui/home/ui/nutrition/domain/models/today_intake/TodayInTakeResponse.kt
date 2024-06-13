package com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake

import com.modarb.android.network.models.BaseResponse

data class TodayInTakeResponse(
    val `data`: Data,
) : BaseResponse()