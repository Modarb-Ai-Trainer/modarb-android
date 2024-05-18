package com.modarb.android.ui.home.ui.plan.persentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.network.Result
import com.modarb.android.network.RetrofitService
import com.modarb.android.ui.home.ui.plan.data.PlanRepositoryImp
import com.modarb.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.modarb.android.ui.home.ui.plan.domain.usecase.PlanPageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanViewModel : ViewModel() {


    private var apiService = RetrofitService.createService()
    private var myPlanRepository = PlanRepositoryImp(apiService)
    private var myPlanPageUseCase = PlanPageUseCase(myPlanRepository)


    private val _planPageResponse = MutableStateFlow<Result<PlanPageResponse>?>(null)
    val planResponse: StateFlow<Result<PlanPageResponse>?> get() = _planPageResponse

//    private val _customWorkoutsResponse = MutableLiveData<Response<PlanPageResponse>>()
//    val customWorkoutsResponse: LiveData<Response<PlanPageResponse>> = _customWorkoutsResponse

    fun getPlanPage(workoutId: String, token: String) {
        viewModelScope.launch {
            val response = myPlanPageUseCase.invoke(workoutId, token)
            _planPageResponse.value = response
        }
    }

//    fun getCustomWorkouts(ctx: Context) {
//        viewModelScope.launch {
//            val response = planRepo.getCustomWorkouts(ctx)
//            _customWorkoutsResponse.value = response
//        }
//    }
}