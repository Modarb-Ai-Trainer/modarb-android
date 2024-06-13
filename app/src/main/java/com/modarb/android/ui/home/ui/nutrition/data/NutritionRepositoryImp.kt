package com.modarb.android.ui.home.ui.plan.data

import com.google.gson.Gson
import com.modarb.android.network.ApiResult
import com.modarb.android.network.ApiService
import com.modarb.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse

class NutritionRepositoryImp(private val apiService: ApiService) : NutritionRepository {

    override suspend fun getTodayMeal(
        token: String
    ): ApiResult<TodayMealsResponse> {

        return try {
            val response = apiService.getToadyMeals(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, TodayMealsResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getDailyGoals(
        token: String
    ): ApiResult<DailyGoalsResponse> {

        return try {
            val response = apiService.getDailyGoals(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, DailyGoalsResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getTodayInTake(token: String): ApiResult<TodayInTakeResponse> {

        return try {
            val response = apiService.getTodayInTake(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, TodayInTakeResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getMyMealPlan(token: String): ApiResult<MyMealPlanResponse> {
        return try {
            val response = apiService.getMyMealPlan(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, MyMealPlanResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }

    override suspend fun getAllMealsPlans(token: String): ApiResult<AllMealsPlansResponse> {
        return try {
            val response = apiService.getAllMealsPlan(token)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Failure(Throwable("Response body is null"))
            } else {
                val errorResponse = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorResponse, AllMealsPlansResponse::class.java)
                ApiResult.Error(parsedError)
            }
        } catch (e: Exception) {
            ApiResult.Failure(e)
        }
    }


}
