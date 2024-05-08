package com.modarb.android.network

import com.modarb.android.ui.home.ui.home.models.HomePageResponse
import com.modarb.android.ui.home.ui.plan.models.PlanPageResponse
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


    // home page
    @GET("api/v1/user/homePage/")
    suspend fun getHomePage(
        @Header("Authorization") token: String
    ): Response<HomePageResponse>


    // my plan page
    @GET("api/v1/user/myWorkouts/{id}")
    suspend fun getPlanPage(
        @Path("id") userId: String, @Header("Authorization") token: String
    ): Response<PlanPageResponse>
}

