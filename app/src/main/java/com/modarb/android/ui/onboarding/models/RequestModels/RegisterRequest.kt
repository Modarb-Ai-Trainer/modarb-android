package com.modarb.android.ui.onboarding.models.RequestModels

data class RegisterRequest(
    var confirmPassword: String,
    var dob: String,
    var email: String,
    var fitness_level: String,
    var gender: String,
    var height: Int,
    var injuries: List<String>,
    var name: String,
    var password: String,
    var preferences: Preferences,
    var weight: Int
)