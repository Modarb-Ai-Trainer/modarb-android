package com.modarb.android.ui.home.ui.plan.persentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.modarb.android.network.ApiResult
import com.modarb.android.network.RetrofitService
import com.modarb.android.ui.home.ui.plan.data.PlanRepositoryImp
import com.modarb.android.ui.home.ui.plan.domain.models.CreateCustomWorkoutRequest
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import com.modarb.android.ui.home.ui.plan.domain.models.customworkout.create.CreateCustomWorkoutResponse
import com.modarb.android.ui.home.ui.plan.domain.usecase.CreateCustomWorkoutUseCase
import com.modarb.android.ui.home.ui.plan.domain.usecase.GetCustomWorkoutUseCase
import com.modarb.android.ui.home.ui.plan.domain.usecase.PlanPageUseCase
import com.modarb.android.ui.home.ui.plan.domain.usecase.SearchExercisesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PlanViewModel : ViewModel() {


    private var apiService = RetrofitService.createService()
    private var myPlanRepository = PlanRepositoryImp(apiService)
    private var myPlanPageUseCase = PlanPageUseCase(myPlanRepository)
    private var getCustomWorkoutUseCase = GetCustomWorkoutUseCase(myPlanRepository)
    private var searchExercisesUseCase = SearchExercisesUseCase(myPlanRepository)
    private var createCustomWorkoutUseCase = CreateCustomWorkoutUseCase(myPlanRepository)


//    private var getExercisesUseCase = GetExercisesUseCase(myPlanRepository)

    private val _planPageResponse = MutableStateFlow<ApiResult<PlanPageResponse>?>(null)
    val planResponse: StateFlow<ApiResult<PlanPageResponse>?> get() = _planPageResponse

    private var _customWorkoutsResponse = MutableStateFlow<ApiResult<CustomWorkoutResponse>?>(null)
    val customWorkoutResponse: StateFlow<ApiResult<CustomWorkoutResponse>?> get() = _customWorkoutsResponse

    private var _getAllExercises = MutableStateFlow<ApiResult<ExercisesResponse>?>(null)
    val getExercise: StateFlow<ApiResult<ExercisesResponse>?> get() = _getAllExercises

    private var _createCustomWorkoutResponse =
        MutableStateFlow<ApiResult<CreateCustomWorkoutResponse>?>(null)
    val createCustomWorkoutResponse: StateFlow<ApiResult<CreateCustomWorkoutResponse>?> get() = _createCustomWorkoutResponse


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

    fun getPaginatedExercises(
        token: String, filterName: String, filterVal: String
    ): Flow<PagingData<com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data>> {
        return myPlanRepository.getExercisesPagingData(token, filterName, filterVal)
            .cachedIn(viewModelScope)
    }

//    fun getAllExercises(token: String, filter: String,page: Int, limit: Int) {
//        viewModelScope.launch {
//            val response = getExercisesUseCase.invoke(token,filter,page,limit)
//            _getAllExercises.value = response
//        }
//    }

    fun searchExercise(token: String, search: String, filter: String) {
        viewModelScope.launch {
            val response = searchExercisesUseCase.invoke(token, search, filter)
            _getAllExercises.value = response
        }
    }

    fun createCustomWorkout(token: String, createCustomWorkoutRequest: CreateCustomWorkoutRequest) {
        viewModelScope.launch {
            val response = createCustomWorkoutUseCase.invoke(token, createCustomWorkoutRequest)
            _createCustomWorkoutResponse.value = response
        }
    }


}