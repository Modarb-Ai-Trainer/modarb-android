package com.modarb.android.network

import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.ui.home.models.HomePageResponse
import com.modarb.android.ui.home.ui.plan.models.PlanPageResponse
import com.modarb.android.ui.onboarding.models.LoginResponse
import com.modarb.android.ui.onboarding.models.RequestModels.LoginRequest
import com.modarb.android.ui.onboarding.models.RequestModels.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
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


    // My Plan Page
    @GET("api/v1/user/myWorkouts/{id}")
    suspend fun getPlanPage(
        @Path("id") myWorkoutId: String, @Header("Authorization") token: String
    ): Response<PlanPageResponse>

    // Workouts

    @PATCH("/api/v1/user/myWorkouts/{id}/progress/{week}/{day}")
    suspend fun markDoneWorkout(
        @Path("id") myWorkoutId: String,
        @Path("week") week: Int,
        @Path("day") day: Int,
        @Header("Authorization") token: String
    ): Response<BaseResponse>


    // Custom workouts page
    @GET("api/v1/user/templates/")
    suspend fun getCustomWorkouts(
        @Header("Authorization") token: String
    ): Response<PlanPageResponse>


}

