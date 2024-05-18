package com.modarb.android.ui.home.ui.home.domain.models

import com.modarb.android.network.models.BaseResponse

data class HomePageResponse(
    val `data`: Data
) : BaseResponse()