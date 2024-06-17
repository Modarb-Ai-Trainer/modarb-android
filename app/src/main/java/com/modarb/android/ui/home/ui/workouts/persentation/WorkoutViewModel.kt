package com.modarb.android.ui.home.ui.workouts.persentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.modarb.android.network.ApiResult
import com.modarb.android.network.RetrofitService
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.ui.plan.data.PlanRepositoryImp
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.modarb.android.ui.home.ui.plan.domain.usecase.SearchExercisesUseCase
import com.modarb.android.ui.home.ui.workouts.data.WorkoutRepoImpl
import com.modarb.android.ui.home.ui.workouts.domain.EnrollWorkoutProgramUseCase
import com.modarb.android.ui.home.ui.workouts.domain.GetWorkoutProgramsUseCase
import com.modarb.android.ui.home.ui.workouts.models.Workout
import com.modarb.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {


    private var apiService = RetrofitService.createService()
    private var myPlanRepository = PlanRepositoryImp(apiService)
    private var workoutProgramsRepo = WorkoutRepoImpl(apiService)
    private var workoutsUseCase = GetWorkoutProgramsUseCase(workoutProgramsRepo)
    private var enrollWorkoutsUseCase = EnrollWorkoutProgramUseCase(workoutProgramsRepo)

    private var searchExercisesUseCase = SearchExercisesUseCase(myPlanRepository)


    private val _planPageResponse = MutableStateFlow<ApiResult<PlanPageResponse>?>(null)
    val planResponse: StateFlow<ApiResult<PlanPageResponse>?> get() = _planPageResponse

    private var _getAllExercises = MutableStateFlow<ApiResult<ExercisesResponse>?>(null)
    val getExercise: StateFlow<ApiResult<ExercisesResponse>?> get() = _getAllExercises

    private var _getAllPrograms = MutableStateFlow<ApiResult<WorkoutProgramsResponse>?>(null)
    val getWorkoutPrograms: StateFlow<ApiResult<WorkoutProgramsResponse>?> get() = _getAllPrograms
    private val _enrollWorkout = MutableSharedFlow<ApiResult<BaseResponse>>(replay = 0)
    val enrollWorkout: SharedFlow<ApiResult<BaseResponse>> get() = _enrollWorkout

    fun getPaginatedExercises(
        token: String, filterName: String, filterVal: String
    ): Flow<PagingData<com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data>> {
        return myPlanRepository.getExercisesPagingData(token, filterName, filterVal)
            .cachedIn(viewModelScope)
    }

    fun getWorkoutPrograms(token: String) {
        viewModelScope.launch {
            val response = workoutsUseCase.invoke(token)
            _getAllPrograms.value = response
        }
    }

    fun enrollWorkoutProgram(token: String, workoutId: Workout) {
        viewModelScope.launch {
            val response = enrollWorkoutsUseCase.invoke(token, workoutId)
            _enrollWorkout.emit(response)
        }
    }


    fun searchExercise(token: String, search: String, filter: String) {
        viewModelScope.launch {
            val response = searchExercisesUseCase.invoke(token, search, filter)
            _getAllExercises.value = response
        }
    }
}