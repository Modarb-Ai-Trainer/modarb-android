package com.modarb.android.ui.home.ui.plan.logic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.ui.home.ui.plan.models.PlanPageResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class PlanViewModel(private val homeRepository: PlanRepository) : ViewModel() {

    private val _planResponse = MutableLiveData<Response<PlanPageResponse>>()
    val planResponse: LiveData<Response<PlanPageResponse>> = _planResponse

    fun getPlanPage(ctx: Context) {
        viewModelScope.launch {
            val response = homeRepository.getPlanPage(ctx)
            _planResponse.value = response
        }
    }
}