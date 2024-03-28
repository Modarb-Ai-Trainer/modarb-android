package com.modarb.android.ui.onboarding.utils

import com.modarb.android.ui.onboarding.models.RequestModels.RegisterRequest

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

        fun validateRegistrationData(registerRequest: RegisterRequest): Boolean {
            // Check if any required field is empty
            if (registerRequest.dob.isEmpty() ||
                registerRequest.gender.isEmpty() ||
                registerRequest.fitness_level.isEmpty() ||
                registerRequest.preferences.fitness_goal.isEmpty() ||
                registerRequest.preferences.workout_place.isEmpty()
            ) {
                return false
            }

            return registerRequest.preferences.preferred_equipment.isNotEmpty()
        }

    }
}
