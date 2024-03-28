package com.modarb.android.ui.onboarding.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.modarb.android.R
import com.modarb.android.databinding.ActivityRegisterScreenBinding
import com.modarb.android.network.RetrofitService
import com.modarb.android.ui.onboarding.models.UserRegisterData
import com.modarb.android.ui.onboarding.utils.ValidationUtil
import com.modarb.android.ui.onboarding.viewModel.UserRepository
import com.modarb.android.ui.onboarding.viewModel.UserViewModel
import com.modarb.android.ui.onboarding.viewModel.UserViewModelFactory

class RegisterScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterScreenBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViewModels()
        doRegister()
    }

    private fun initViewModels() {
        val userRepository = UserRepository(RetrofitService.createService())
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]
    }

    private fun doRegister() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText2.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
            UserRegisterData.registerRequest.dob = "2023/2/2"

            if (isValidInput()) {
                //TODO make a request
                UserRegisterData.registerRequest.name = name
                UserRegisterData.registerRequest.email = email
                UserRegisterData.registerRequest.password = password
                UserRegisterData.registerRequest.confirmPassword = confirmPassword
                UserRegisterData.printData()
                binding.progessView.progressOverlay.visibility = View.VISIBLE
                viewModel.registerUser(UserRegisterData.registerRequest)
            }
        }
        getResponse()
    }

    private fun getResponse() {
        viewModel.registerResponse.observe(this) { response ->
            RetrofitService.handleRequest(
                response = response,
                onSuccess = { loginResponse ->
                    if (loginResponse.status == 200) {
                        Toast.makeText(
                            this,
                            getString(R.string.welcome_please_login_to_complete_your_register),
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(this, WelcomeScreenActivity::class.java)
                        intent.putExtra("register", true)
                        startActivity(intent)
                        finish()

                    }
                },
                onError = { errorResponse ->
                    val defaultErrorMessage = getString(R.string.an_error_occurred)
                    val message = errorResponse?.errors?.firstOrNull() ?: errorResponse?.error
                    ?: defaultErrorMessage
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            )
            binding.progessView.progressOverlay.visibility = View.GONE

        }
    }

    private fun isValidInput(): Boolean {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText2.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast(getString(R.string.please_fill_all_the_data))
            return false
        }

        if (!ValidationUtil.validateEmail(email)) {
            showToast(getString(R.string.write_a_valid_email))
            return false
        }

        if (!ValidationUtil.validatePasswordLength(password)) {
            showToast(getString(R.string.enter_at_least_8_characters))
            return false
        }

        if (!ValidationUtil.validatePasswordMatch(password, confirmPassword)) {
            showToast(getString(R.string.password_don_t_match))
            return false
        }


        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this@RegisterScreenActivity, message, Toast.LENGTH_SHORT).show()
    }


}