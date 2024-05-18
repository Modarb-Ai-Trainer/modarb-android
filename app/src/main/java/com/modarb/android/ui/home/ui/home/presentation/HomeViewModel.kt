package com.modarb.android.ui.home.ui.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.network.Result
import com.modarb.android.network.RetrofitService
import com.modarb.android.network.models.BaseResponse
import com.modarb.android.ui.home.ui.home.data.HomeRepositoryImpl
import com.modarb.android.ui.home.ui.home.domain.models.HomePageResponse
import com.modarb.android.ui.home.ui.home.domain.usecase.HomePageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var apiService = RetrofitService.createService()
    private var homeRepository = HomeRepositoryImpl(apiService)
    private var homeUseCase = HomePageUseCase(homeRepository)

    private val _homeResponse = MutableStateFlow<Result<HomePageResponse>?>(null)
    val homeResponse: StateFlow<Result<BaseResponse>?> get() = _homeResponse


    fun getUserHomePage(token: String) {
        viewModelScope.launch {
            val result = homeUseCase.invoke(token)
            _homeResponse.value = result
        }

    }
}