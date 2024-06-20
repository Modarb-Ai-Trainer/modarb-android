package com.modarb.android.network

import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse
import com.modarb.android.ui.home.ui.nutrition.PlanBody
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
import com.modarb.android.ui.home.ui.nutrition.models.AddCustomMealBody
import com.modarb.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse
import com.modarb.android.ui.home.ui.plan.domain.models.CreateCustomWorkoutRequest
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.create.CreateCustomWorkoutResponse
import com.modarb.android.ui.home.ui.workouts.models.Workout
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse
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
import retrofit2.http.Query

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
    // TODO add paging
    @GET("api/v1/user/templates?limit=200")
    suspend fun getCustomWorkouts(
        @Header("Authorization") token: String
    ): Response<CustomWorkoutResponse>

    // TODO add paging
    @GET("api/v1/user/workouts?limit=200")
    suspend fun getWorkoutPrograms(
        @Header("Authorization") token: String,
    ): Response<WorkoutProgramsResponse>

    @POST("api/v1/user/myWorkouts")
    suspend fun enrollWorkoutProgram(
        @Header("Authorization") token: String, @Body workoutId: Workout
    ): Response<BaseResponse>

    // Exercises Selection APIs
    @GET("api/v1/user/exercises/")
    suspend fun getExercises(
        @Header("Authorization") token: String,
        @Query("filterName") filterCat: String,
        @Query("filterVal") filterVal: String,
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<ExercisesResponse>

    @GET("api/v1/user/exercises/search")
    suspend fun getExercisesSearch(
        @Header("Authorization") token: String,
        @Query("searchTerm") search: String,
        @Query("filter") filter: String
    ): Response<ExercisesResponse>

    // Create custom workout
    @POST("api/v1/user/templates")
    suspend fun createCustomWorkout(
        @Header("Authorization") token: String,
        @Body customWorkoutRequest: CreateCustomWorkoutRequest
    ): Response<CreateCustomWorkoutResponse>

    // Nutrition API's

    @GET("api/v1/user/nutri-guide/today-meals")
    suspend fun getToadyMeals(
        @Header("Authorization") token: String,
    ): Response<TodayMealsResponse>

    @GET("api/v1/user/nutri-guide/daily-goals")
    suspend fun getDailyGoals(
        @Header("Authorization") token: String,
    ): Response<DailyGoalsResponse>

    @GET("api/v1/user/nutri-guide/todays-intake")
    suspend fun getTodayInTake(
        @Header("Authorization") token: String,
    ): Response<TodayInTakeResponse>


    @GET("api/v1/user/myMealPlan")
    suspend fun getMyMealPlan(
        @Header("Authorization") token: String,
    ): Response<MyMealPlanResponse>

    @GET("api/v1/user/mealPlans")
    suspend fun getAllMealsPlan(
        @Header("Authorization") token: String,
    ): Response<AllMealsPlansResponse>

    @GET("/api/v1/user/homePage/your-daily-intake")
    suspend fun getHomePageTodayInTake(
        @Header("Authorization") token: String,
    ): Response<TodayInTakeResponse>

    // Ingredients
    @GET("/api/v1/user/ingredients")
    suspend fun getIngredients(
        @Header("Authorization") token: String, @Query("skip") skip: Int, @Query("limit") limit: Int
    ): Response<IngredientsResponse>

    //TODO handle paging here
    @GET("/api/v1/user/ingredients/search?limit=50")
    suspend fun searchIngredients(
        @Header("Authorization") token: String, @Query("searchTerm") search: String
    ): Response<IngredientsResponse>

    @POST("/api/v1/user/meals/eat-custom-meal")
    suspend fun addCustomMeal(
        @Header("Authorization") token: String, @Body data: AddCustomMealBody
    ): Response<BaseResponse>

    @POST("/api/v1/user/myMealPlan")
    suspend fun enrollIntoPlanProgram(
        @Header("Authorization") token: String, @Body data: PlanBody
    ): Response<BaseResponse>

}

