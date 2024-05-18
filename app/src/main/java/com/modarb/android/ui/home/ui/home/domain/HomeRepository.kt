package com.modarb.android.ui.home.ui.home.domain

import com.modarb.android.network.Result
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse

interface HomeRepository {

    suspend fun getHomePage(token: String): Result<HomePageResponse>
}