package com.modarb.android.network.models

open class BaseResponse {
    var status: Int = 0
    lateinit var message: String
    lateinit var errors: List<String>
    lateinit var error: String
    override fun toString(): String {
        return "BaseResponse(status=$status, message='$message', errors=$errors, error='$error')"
    }

}
