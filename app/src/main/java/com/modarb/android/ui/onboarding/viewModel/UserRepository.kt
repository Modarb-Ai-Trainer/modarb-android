package com.modarb.android.ui.onboarding.viewModel

import com.modarb.android.network.ApiService
import com.modarb.android.ui.onboarding.models.LoginResponse
import com.modarb.android.ui.onboarding.models.RequestModels.LoginRequest
import com.modarb.android.ui.onboarding.models.RequestModels.RegisterRequest
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return apiService.loginUser(LoginRequest(email, password))
    }

    suspend fun registerUser(registerRequest: RegisterRequest): Response<LoginResponse> {
        return apiService.registerUser(registerRequest)
    }
}
