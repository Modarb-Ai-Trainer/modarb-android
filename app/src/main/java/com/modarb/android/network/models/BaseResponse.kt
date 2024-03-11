package com.modarb.android.network.models

data class BaseResponse(
    val code: Int,
    val message: String,
    val errors: List<String>,
    val success: Boolean,
    val status: Boolean
)