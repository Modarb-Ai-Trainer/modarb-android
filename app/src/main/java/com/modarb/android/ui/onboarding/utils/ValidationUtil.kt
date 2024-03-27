package com.modarb.android.ui.onboarding.utils

class ValidationUtil {

    companion object {

        fun validateEmail(email: String): Boolean {
            val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
            return email.matches(emailRegex.toRegex())
        }

        fun validatePasswordLength(password: String): Boolean {
            return password.length >= 8
        }

        fun validatePasswordMatch(password: String, confirmPassword: String): Boolean {
            return password == confirmPassword
        }
    }
}
