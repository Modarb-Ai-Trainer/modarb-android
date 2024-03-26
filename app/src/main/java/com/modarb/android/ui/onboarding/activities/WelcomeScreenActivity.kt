package com.modarb.android.ui.onboarding.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.modarb.android.R
import com.modarb.android.databinding.ActivityWelcomeScreenBinding
import com.modarb.android.network.RetrofitService
import com.modarb.android.network.RetrofitService.handleRequest
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil
import com.modarb.android.ui.onboarding.viewModel.UserRepository
import com.modarb.android.ui.onboarding.viewModel.UserViewModel
import com.modarb.android.ui.onboarding.viewModel.UserViewModelFactory


class WelcomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeScreenBinding
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
        initViewModels()
    }

    private fun init() {

        initBottomSheet()
        binding.loginTextView.setOnClickListener {
            if (!bottomSheet.isShowing) {
                showLogin()
            }
        }

        binding.startButton.setOnClickListener {
            val intent = Intent(this, OnBoardingSplashActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initBottomSheet() {
        bottomSheet = BottomSheetDialog(this)
    }

    private fun initViewModels() {
        val userRepository = UserRepository(RetrofitService.createService())
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]

    }

    private fun showLogin() {
        bottomSheet.setContentView(R.layout.login_view)
        bottomSheet.show()

        val loginBtn = bottomSheet.findViewById<Button>(R.id.loginBtn)
        val emailEditText = bottomSheet.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = bottomSheet.findViewById<TextInputEditText>(R.id.passwordEditText)

        loginBtn?.setOnClickListener {
            val email = emailEditText?.text.toString()
            val password = passwordEditText?.text.toString()
            // TODO add validation
            viewModel.loginUser(email, password)
        }

        viewModel.loginResponse.observe(this) { response ->
            handleRequest(
                response = response,
                onSuccess = { loginResponse ->
                    if (loginResponse.status == 200) {
                        UserPrefUtil.saveUserData(this, loginResponse.data)
                        UserPrefUtil.setUserLoggedIn(this, true)
                        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                    }
                },
                onError = { errorResponse ->
                    val defaultErrorMessage = getString(R.string.an_error_occurred)
                    val message = errorResponse?.errors?.firstOrNull() ?: errorResponse?.error
                    ?: defaultErrorMessage
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            )
        }

    }
}



