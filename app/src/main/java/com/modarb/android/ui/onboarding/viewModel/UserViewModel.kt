package com.modarb.android.ui.onboarding.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modarb.android.ui.onboarding.models.LoginResponse
import com.modarb.android.ui.onboarding.models.RequestModels.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginResponse = MutableLiveData<Response<LoginResponse>>()
    val loginResponse: LiveData<Response<LoginResponse>> = _loginResponse

    private val _registerResponse = MutableLiveData<Response<LoginResponse>>()
    val registerResponse: LiveData<Response<LoginResponse>> = _registerResponse

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val response = userRepository.loginUser(email, password)
            _loginResponse.value = response
        }
    }

    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            val response = userRepository.registerUser(registerRequest)
            _registerResponse.value = response
        }
    }
}
