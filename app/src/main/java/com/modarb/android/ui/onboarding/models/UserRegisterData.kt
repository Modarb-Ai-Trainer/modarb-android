package com.modarb.android.ui.onboarding.models

import com.modarb.android.ui.onboarding.models.RequestModels.Preferences
import com.modarb.android.ui.onboarding.models.RequestModels.RegisterRequest


/*
Singleton class.
 */
object UserRegisterData {
    var registerRequest: RegisterRequest = RegisterRequest(
        confirmPassword = "",
        dob = "",
        email = "",
        fitness_level = "",
        gender = "",
        height = 0,
        injuries = emptyList(),
        name = "",
        password = "",
        preferences = Preferences("", emptyList(), 0, ""),
        weight = 0
    )

    fun printData() {
        println("confirmPassword: ${registerRequest.confirmPassword}")
        println("dob: ${registerRequest.dob}")
        println("email: ${registerRequest.email}")
        println("fitness_level: ${registerRequest.fitness_level}")
        println("gender: ${registerRequest.gender}")
        println("height: ${registerRequest.height}")
        println("injuries: ${registerRequest.injuries}")

        println("preferences:")
        println("  fitness_goal: ${registerRequest.preferences.fitness_goal}")
        println("  preferred_equipment: ${registerRequest.preferences.preferred_equipment}")
        println("  target_weight: ${registerRequest.preferences.target_weight}")
        println("  workout_place: ${registerRequest.preferences.workout_place}")


        println("name: ${registerRequest.name}")
        println("password: ${registerRequest.password}")
        println("weight: ${registerRequest.weight}")
    }
}