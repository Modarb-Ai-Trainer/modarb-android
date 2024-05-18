package com.modarb.android.network

import android.content.Context
import android.widget.Toast
import com.modarb.android.R
import com.modarb.android.network.models.BaseResponse

class NetworkHelper {

    companion object {
        fun showErrorMessage(context: Context, errorResponse: BaseResponse) {
            val defaultErrorMessage = context.getString(R.string.an_error_occurred)
            val message =
                errorResponse.errors?.firstOrNull() ?: errorResponse.error ?: defaultErrorMessage
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}