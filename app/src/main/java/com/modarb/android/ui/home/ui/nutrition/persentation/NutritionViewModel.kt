package com.modarb.android.ui.home.ui.nutrition.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.modarb.android.network.ApiResult
import com.modarb.android.network.RetrofitService
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetAllIngredientsUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetAllMyMealsPlansUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetDailyGoalsUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetMyMealPlanUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetTodayInTakeUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.GetTodayMealUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.usecase.SearchIngredientsUseCase
import com.modarb.android.ui.home.ui.nutrition.models.AddCustomMealBody
import com.modarb.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse
import com.modarb.android.ui.home.ui.plan.data.NutritionRepositoryImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class NutritionData(
    val todayMeals: ApiResult<TodayMealsResponse>?,
    val dailyGoals: ApiResult<DailyGoalsResponse>?,
    val todayInTake: ApiResult<TodayInTakeResponse>?,
    val myMealPlan: ApiResult<MyMealPlanResponse>?,
    val allMealsPlans: ApiResult<AllMealsPlansResponse>?
)

class NutritionViewModel : ViewModel() {

    private var apiService = RetrofitService.createService()
    private var nutritionRepository = NutritionRepositoryImp(apiService)
    private var todayMealUseCase = GetTodayMealUseCase(nutritionRepository)
    private var getDailyGoalsUseCase = GetDailyGoalsUseCase(nutritionRepository)
    private var getTodayInTakeUseCase = GetTodayInTakeUseCase(nutritionRepository)
    private var myMealPlanUseCase = GetMyMealPlanUseCase(nutritionRepository)
    private var allMyMealsPlansUseCase = GetAllMyMealsPlansUseCase(nutritionRepository)
    private var searchExercisesUseCase = SearchIngredientsUseCase(nutritionRepository)
    private var getAlliNutrientsUseCase = GetAllIngredientsUseCase(nutritionRepository)

    private val _todayMealsResponse = MutableStateFlow<ApiResult<TodayMealsResponse>?>(null)
    private val _getDailyGoalsResponse = MutableStateFlow<ApiResult<DailyGoalsResponse>?>(null)
    private val _getTodayInTake = MutableStateFlow<ApiResult<TodayInTakeResponse>?>(null)
    private val _getMyMealPlan = MutableStateFlow<ApiResult<MyMealPlanResponse>?>(null)
    private val _getAllMealsPlan = MutableStateFlow<ApiResult<AllMealsPlansResponse>?>(null)
    private val _addCustomMeal = MutableSharedFlow<ApiResult<BaseResponse>?>(replay = 0)


    private var _getAllIngredients = MutableStateFlow<ApiResult<IngredientsResponse>?>(null)
    val addCustomMeal: SharedFlow<ApiResult<BaseResponse>?> get() = _addCustomMeal

    val getAllIngredients: StateFlow<ApiResult<IngredientsResponse>?> get() = _getAllIngredients
    val combinedNutritionData: StateFlow<NutritionData> = combine(
        _todayMealsResponse,
        _getDailyGoalsResponse,
        _getTodayInTake,
        _getMyMealPlan,
        _getAllMealsPlan
    ) { todayMeals, dailyGoals, todayInTake, myMealPlan, allMealsPlans ->
        NutritionData(todayMeals, dailyGoals, todayInTake, myMealPlan, allMealsPlans)
    }.stateIn(viewModelScope, SharingStarted.Lazily, NutritionData(null, null, null, null, null))

    fun getAllNutritionData(token: String) {
        viewModelScope.launch {
            launch { _todayMealsResponse.value = todayMealUseCase.invoke(token) }
            launch { _getDailyGoalsResponse.value = getDailyGoalsUseCase.invoke(token) }
            launch { _getTodayInTake.value = getTodayInTakeUseCase.invoke(token) }
            launch { _getMyMealPlan.value = myMealPlanUseCase.invoke(token) }
            launch { _getAllMealsPlan.value = allMyMealsPlansUseCase.invoke(token) }
        }
    }

    fun getPaginatedExercises(
        token: String
    ): Flow<PagingData<com.modarb.android.ui.home.ui.nutrition.models.ingredients.Data>> {
        return nutritionRepository.getIngredientsPagingData(token).cachedIn(viewModelScope)
    }

    fun searchIngredients(token: String, search: String) {
        viewModelScope.launch {
            val response = searchExercisesUseCase.invoke(token, search)
            _getAllIngredients.value = response
        }
    }

    fun addCustomMeal(token: String, data: AddCustomMealBody) {
        viewModelScope.launch {
            val response = nutritionRepository.addCustomMeal(token, data)
            _addCustomMeal.emit(response)
        }
    }
}
