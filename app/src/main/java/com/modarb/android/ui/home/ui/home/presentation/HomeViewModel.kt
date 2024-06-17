package com.modarb.android.ui.home.ui.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.network.ApiResult
import com.modarb.android.network.RetrofitService
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.ui.home.data.HomeRepositoryImpl
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse
import com.modarb.android.ui.home.ui.home.domain.usecase.HomePageUseCase
import com.modarb.android.ui.home.ui.home.domain.usecase.TodayInTakeUseCase
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var apiService = RetrofitService.createService()
    private var homeRepository = HomeRepositoryImpl(apiService)
    private var homeUseCase = HomePageUseCase(homeRepository)

    private var todayInTakeUseCase = TodayInTakeUseCase(homeRepository)

    private val _homeResponse = MutableStateFlow<ApiResult<HomePageResponse>?>(null)
    val homeResponse: StateFlow<ApiResult<BaseResponse>?> get() = _homeResponse

    private val _getTodayInTake = MutableStateFlow<ApiResult<TodayInTakeResponse>?>(null)
    val todayInTake: StateFlow<ApiResult<TodayInTakeResponse>?> get() = _getTodayInTake

    fun getUserHomePage(token: String) {
        viewModelScope.launch {
            val result = homeUseCase.invoke(token)
            _homeResponse.value = result
        }
    }

    fun getTodayInTake(token: String) {
        viewModelScope.launch {
            val result = todayInTakeUseCase.invoke(token)
            _getTodayInTake.value = result
        }
    }
}