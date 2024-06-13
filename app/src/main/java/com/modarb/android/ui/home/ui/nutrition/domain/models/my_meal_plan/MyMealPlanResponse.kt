package com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan

import com.modarb.android.network.models.BaseResponse

data class MyMealPlanResponse(
    val `data`: Data,
) : BaseResponse()