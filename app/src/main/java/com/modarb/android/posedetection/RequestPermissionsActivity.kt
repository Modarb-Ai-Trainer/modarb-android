package com.modarb.android.posedetection

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.modarb.android.R
import com.modarb.android.posedetection.Utils.PermissionResultCallback

class RequestPermissionsActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_permissions)

        if (!allRuntimePermissionsGranted()) {
            getRuntimePermissions()
        } else {
            onPermissionGranted()
        }
    }

    override fun onPermissionGranted() {
        Log.i("Great", "All permissions granted")
        startActivity(Intent(this, CameraActivity::class.java))
    }

    private fun allRuntimePermissionsGranted(): Boolean {
        for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
            permission.let {
                if (!isPermissionGranted(this, it)) {
                    return false
                }
            }
        }
        return true
    }

    private fun getRuntimePermissions() {
        val permissionsToRequest = ArrayList<String>()
        for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
            permission.let {
                if (!isPermissionGranted(this, it)) {
                    permissionsToRequest.add(permission)
                }
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this, permissionsToRequest.toTypedArray(), PERMISSION_REQUESTS
            )
        } else {
            onPermissionGranted()
        }
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(
                context, permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        Log.i("PERM", "Permission NOT granted: $permission")
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUESTS) {
            if (allRuntimePermissionsGranted()) {
                onPermissionGranted()
            }
        }
    }

    companion object {
        private const val TAG = "RequestPermissionsActivity"
        private const val PERMISSION_REQUESTS = 1

        private val REQUIRED_RUNTIME_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
}

