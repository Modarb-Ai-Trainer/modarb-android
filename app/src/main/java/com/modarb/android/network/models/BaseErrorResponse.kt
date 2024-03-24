package com.modarb.android.network.models

data class BaseErrorResponse(
    val code: Int,
    val errors: List<String>,
    val success: Boolean
)