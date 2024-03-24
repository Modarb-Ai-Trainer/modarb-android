package com.modarb.android.ui.onboarding.viewModel

import com.modarb.android.network.ApiService
import com.modarb.android.ui.onboarding.models.LoginResponse
import com.modarb.android.ui.onboarding.models.RequestModels.LoginRequest
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return apiService.loginUser(LoginRequest(email, password))
    }
}