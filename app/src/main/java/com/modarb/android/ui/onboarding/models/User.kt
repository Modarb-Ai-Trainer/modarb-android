package com.modarb.android.ui.onboarding.models

data class user(
    val age: Int,
    val email: String,
    val fitness_level: String,
    val gender: String,
    val height: Int,
    val id: String,
    val injuries: List<String>,
    val name: String,
    val preferences: Preferences,
    val role: String,
    val weight: Int
)