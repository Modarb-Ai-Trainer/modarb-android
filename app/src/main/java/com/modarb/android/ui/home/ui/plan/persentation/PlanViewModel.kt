package com.modarb.android.ui.home.ui.plan.persentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.network.ApiResult
import com.modarb.android.network.RetrofitService
import com.modarb.android.ui.home.ui.plan.data.PlanRepositoryImp
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import com.modarb.android.ui.home.ui.plan.domain.usecase.GetCustomWorkoutUseCase
import com.modarb.android.ui.home.ui.plan.domain.usecase.PlanPageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PlanViewModel : ViewModel() {


    private var apiService = RetrofitService.createService()
    private var myPlanRepository = PlanRepositoryImp(apiService)
    private var myPlanPageUseCase = PlanPageUseCase(myPlanRepository)
    private var getCustomWorkoutUseCase = GetCustomWorkoutUseCase(myPlanRepository)


    private val _planPageResponse = MutableStateFlow<ApiResult<PlanPageResponse>?>(null)
    val planResponse: StateFlow<ApiResult<PlanPageResponse>?> get() = _planPageResponse

    private var _customWorkoutsResponse = MutableStateFlow<ApiResult<CustomWorkoutResponse>?>(null)
    val customWorkoutResponse: StateFlow<ApiResult<CustomWorkoutResponse>?> get() = _customWorkoutsResponse


    private val _combinedResponses =
        MutableStateFlow<Pair<ApiResult<PlanPageResponse>?, ApiResult<CustomWorkoutResponse>?>>(null to null)
    val combinedResponses: StateFlow<Pair<ApiResult<PlanPageResponse>?, ApiResult<CustomWorkoutResponse>?>> get() = _combinedResponses

    init {
        viewModelScope.launch {
            combine(_planPageResponse, _customWorkoutsResponse) { planResponse, workoutsResponse ->
                planResponse to workoutsResponse
            }.collect { combined ->
                _combinedResponses.value = combined
            }
        }
    }

    fun getPlanPage(workoutId: String, token: String) {
        viewModelScope.launch {
            val response = myPlanPageUseCase.invoke(workoutId, token)
            _planPageResponse.value = response
        }
    }

    fun getCustomWorkouts(token: String) {
        viewModelScope.launch {
            val response = getCustomWorkoutUseCase.invoke(token)
            _customWorkoutsResponse.value = response
        }
    }
}