package com.modarb.android.ui.home.ui.plan.logic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.ui.home.ui.plan.models.PlanPageResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class PlanViewModel(private val planRepo: PlanRepository) : ViewModel() {

    private val _planResponse = MutableLiveData<Response<PlanPageResponse>>()
    val planResponse: LiveData<Response<PlanPageResponse>> = _planResponse

    private val _customWorkoutsResponse = MutableLiveData<Response<PlanPageResponse>>()
    val customWorkoutsResponse: LiveData<Response<PlanPageResponse>> = _customWorkoutsResponse

    fun getPlanPage(ctx: Context) {
        viewModelScope.launch {
            val response = planRepo.getPlanPage(ctx)
            _planResponse.value = response
        }
    }

    fun getCustomWorkouts(ctx: Context) {
        viewModelScope.launch {
            val response = planRepo.getPlanPage(ctx)
            _customWorkoutsResponse.value = response
        }
    }
}