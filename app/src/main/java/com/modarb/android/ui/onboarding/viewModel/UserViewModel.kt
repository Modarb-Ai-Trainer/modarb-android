package com.modarb.android.ui.onboarding.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.ui.onboarding.models.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginResponse = MutableLiveData<Response<LoginResponse>>()
    val loginResponse: LiveData<Response<LoginResponse>> = _loginResponse

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val response = userRepository.loginUser(email, password)
            _loginResponse.value = response
        }
    }
}
