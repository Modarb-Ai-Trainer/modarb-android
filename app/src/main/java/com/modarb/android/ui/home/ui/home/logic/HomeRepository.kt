package com.modarb.android.ui.home.ui.home.logic

import android.content.Context
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.home.models.HomePageResponse
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import retrofit2.Response

class HomeRepository(private val apiService: ApiService) {

    suspend fun getUserHomePage(context: Context): Response<HomePageResponse> {
        return apiService.getHomePage(
            "Bearer " + UserPrefUtil.getUserData(context)!!.token
        )
    }


}
