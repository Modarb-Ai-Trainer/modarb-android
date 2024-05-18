package com.modarb.android.ui.home.ui.home.domain

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse

interface HomeRepository {

    suspend fun getHomePage(token: String): ApiResult<HomePageResponse>
}