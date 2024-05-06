package com.modarb.android.ui.home.ui.home.logic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.ui.home.ui.home.models.HomePageResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _homeResponse = MutableLiveData<Response<HomePageResponse>>()
    val homeResponse: LiveData<Response<HomePageResponse>> = _homeResponse


    fun getUserHomePage(ctx: Context) {
        viewModelScope.launch {
            val response = homeRepository.getUserHomePage(ctx)
            _homeResponse.value = response
        }
    }
}