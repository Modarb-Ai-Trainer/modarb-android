package com.modarb.android.ui.home.ui.nutrition.domain

import com.modarb.android.network.ApiResult
import com.modarb.android.ui.home.ui.nutrition.domain.models.TodayMealsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse

interface NutritionRepository {

    suspend fun getTodayMeal(token: String): ApiResult<TodayMealsResponse>
    suspend fun getDailyGoals(token: String): ApiResult<DailyGoalsResponse>
    suspend fun getTodayInTake(token: String): ApiResult<TodayInTakeResponse>
    suspend fun getMyMealPlan(token: String): ApiResult<MyMealPlanResponse>

    suspend fun getAllMealsPlans(token: String): ApiResult<AllMealsPlansResponse>

}