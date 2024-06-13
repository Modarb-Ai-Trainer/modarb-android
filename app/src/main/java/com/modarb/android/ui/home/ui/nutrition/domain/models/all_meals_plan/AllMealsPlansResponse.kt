package com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan

import com.modarb.android.network.models.BaseResponse

data class AllMealsPlansResponse(
    val `data`: List<Data>,
    val meta: Meta,
) : BaseResponse()