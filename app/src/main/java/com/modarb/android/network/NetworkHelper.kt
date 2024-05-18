package com.modarb.android.network

import android.content.Context
import android.os.Build
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

        fun isEmulator(): Boolean {
            val brand = Build.BRAND
            val device = Build.DEVICE
            val model = Build.MODEL
            val product = Build.PRODUCT
            val hardware = Build.HARDWARE
            val fingerprint = Build.FINGERPRINT
            val manufacturer = Build.MANUFACTURER

            return (fingerprint.startsWith("generic") || brand.startsWith("generic") || brand.equals(
                "google",
                ignoreCase = true
            ) || device.startsWith("generic") || model.contains("google_sdk") || model.contains(
                "Emulator"
            ) || model.contains("Android SDK built for x86") || product.contains("sdk_gphone") || product.contains(
                "google_sdk"
            ) || product.contains("sdk") || product.contains("sdk_x86") || product.contains("vbox86p") || hardware.contains(
                "ranchu"
            ) || hardware.contains("goldfish") || hardware.contains("vbox86") || manufacturer.contains(
                "Genymotion"
            ) || fingerprint.contains("generic") || fingerprint.contains("vbox"))

        }

    }
}