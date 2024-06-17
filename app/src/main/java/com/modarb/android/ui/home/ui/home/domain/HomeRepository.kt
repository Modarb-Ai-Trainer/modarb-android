package com.modarb.android.ui.home.ui.home.domain

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse

interface HomeRepository {

    suspend fun getHomePage(token: String): ApiResult<HomePageResponse>
    suspend fun getTodayInTake(token: String): ApiResult<TodayInTakeResponse>

}