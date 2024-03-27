package com.modarb.android.ui.onboarding.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.R
import com.modarb.android.databinding.ActivityRegisterScreenBinding
import com.modarb.android.ui.onboarding.utils.ValidationUtil

class RegisterScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterScreenBinding.inflate(layoutInflater)
        val view = binding.root
    }


    fun doRegister() {
        var name = binding.nameEditText.text.toString()
        var email = binding.emailEditText2.text.toString()
        var password = binding.passwordEditText.text.toString()
        var confirmPassword = binding.confirmPasswordEditText.toString()

        binding.registerButton.setOnClickListener {
            if (isValidInput()) {
                //TODO make a request
            }
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