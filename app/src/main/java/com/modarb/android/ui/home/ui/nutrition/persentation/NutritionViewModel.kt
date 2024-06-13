package com.modarb.android.ui.home.ui.nutrition.persentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.network.ApiResult
import com.modarb.android.network.RetrofitService
import com.modarb.android.ui.home.ui.nutrition.domain.models.TodayMealsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetAllMyMealsPlansUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetDailyGoalsUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetMyMealPlanUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetTodayInTakeUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetTodayMealUseCase
import com.modarb.android.ui.home.ui.plan.data.NutritionRepositoryImp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutritionViewModel : ViewModel() {

    private var apiService = RetrofitService.createService()
    private var nutritionRepository = NutritionRepositoryImp(apiService)
    private var todayMealUseCase = GetTodayMealUseCase(nutritionRepository)
    private var getDailyGoalsUseCase = GetDailyGoalsUseCase(nutritionRepository)
    private var getTodayInTakeUseCase = GetTodayInTakeUseCase(nutritionRepository)
    private var myMealPlanUseCase = GetMyMealPlanUseCase(nutritionRepository)
    private var allMyMealsPlansUseCase = GetAllMyMealsPlansUseCase(nutritionRepository)


    private val _todayMealsResponse = MutableStateFlow<ApiResult<TodayMealsResponse>?>(null)
    val todayMealsResponse: StateFlow<ApiResult<TodayMealsResponse>?> get() = _todayMealsResponse


    private val _getDailyGoalsResponse = MutableStateFlow<ApiResult<DailyGoalsResponse>?>(null)
    val getDailyGoalsResponse: StateFlow<ApiResult<DailyGoalsResponse>?> get() = _getDailyGoalsResponse


    private val _getTodayInTake = MutableStateFlow<ApiResult<TodayInTakeResponse>?>(null)
    val getTodayInTake: StateFlow<ApiResult<TodayInTakeResponse>?> get() = _getTodayInTake

    private val _getMyMealPlan = MutableStateFlow<ApiResult<MyMealPlanResponse>?>(null)
    val getMyMealPlan: StateFlow<ApiResult<MyMealPlanResponse>?> get() = _getMyMealPlan

    private val _getAllMealsPlan = MutableStateFlow<ApiResult<AllMealsPlansResponse>?>(null)
    val getAllMealsPlans: StateFlow<ApiResult<AllMealsPlansResponse>?> get() = _getAllMealsPlan

    fun getTodayMeals(token: String) {
        viewModelScope.launch {
            val response = todayMealUseCase.invoke(token)
            _todayMealsResponse.value = response
        }
    }

    fun getDailyGoals(token: String) {
        viewModelScope.launch {
            val response = getDailyGoalsUseCase.invoke(token)
            _getDailyGoalsResponse.value = response
        }
    }

    fun getTodayInTake(token: String) {
        viewModelScope.launch {
            val response = getTodayInTakeUseCase.invoke(token)
            _getTodayInTake.value = response
        }
    }

    fun getMyMealPlan(token: String) {
        viewModelScope.launch {
            val response = myMealPlanUseCase.invoke(token)
            _getMyMealPlan.value = response
        }
    }

    fun getAllMealsPlans(token: String) {
        viewModelScope.launch {
            val response = allMyMealsPlansUseCase.invoke(token)
            _getAllMealsPlan.value = response
        }
    }
}