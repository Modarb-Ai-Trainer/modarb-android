package com.modarb.android.ui.home.ui.home.domain.models

data class User(
    val id: String,
    val injuries: List<String>,
    val name: String,
    val preferences: Preferences
)