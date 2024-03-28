package com.modarb.android.network

import com.modarb.android.ui.onboarding.models.LoginResponse
import com.modarb.android.ui.onboarding.models.RequestModels.LoginRequest
import com.modarb.android.ui.onboarding.models.RequestModels.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/users/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/users/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<LoginResponse>
}

