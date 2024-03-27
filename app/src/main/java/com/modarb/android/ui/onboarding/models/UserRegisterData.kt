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
}