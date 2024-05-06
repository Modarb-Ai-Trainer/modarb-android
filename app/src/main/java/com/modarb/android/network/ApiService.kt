package com.modarb.android.network

import com.modarb.android.ui.home.ui.home.models.HomePageResponse
import com.modarb.android.ui.onboarding.models.LoginResponse
import com.modarb.android.ui.onboarding.models.RequestModels.LoginRequest
import com.modarb.android.ui.onboarding.models.RequestModels.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/api/v1/user/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @POST("/api/v1/user/auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<LoginResponse>

    @GET("api/v1/user/myWorkouts/home/{userId}")
    suspend fun getHomePage(
        @Path("userId") userId: String, @Header("Authorization") token: String
    ): Response<HomePageResponse>
}

