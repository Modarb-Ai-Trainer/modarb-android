package com.modarb.android.ui.home.ui.nutrition.models.ingredients

import com.modarb.android.network.models.BaseResponse

data class IngredientsResponse(
    val `data`: List<Data>,
    val meta: Meta,

    ) : BaseResponse()