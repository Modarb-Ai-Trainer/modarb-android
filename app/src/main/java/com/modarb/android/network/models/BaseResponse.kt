package com.modarb.android.network.models

open class BaseResponse {
    var status: Int = 0
    var message: String = ""
    var errors: List<String>? = null
    var error: String? = null
}
