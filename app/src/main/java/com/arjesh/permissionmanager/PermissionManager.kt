package com.arjesh.permissionmanager

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PermissionManager(val activity: AppCompatActivity) {

    companion object {
        lateinit var instance: PermissionManager
        val TAG = "PermissionManager"
    }

    var requestMultiplePermissionLauncher: ActivityResultLauncher<Array<String>>? = null

    var onPermissionGranted: () -> Unit = {}
    var onPermissionDenied: (result: Map<String, Boolean>) -> Unit = {}

    init {
        instance = this
        setLaunchers()
    }

    private fun setLaunchers() {
        requestMultiplePermissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                if (result.values.contains(false)) {
                    onPermissionDenied(result)
                } else {
                    onPermissionGranted()
                }
            }
    }

    fun askPermissions(arrayOfPermissions: Array<String>) {
        val newList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOfPermissions.filter {
                it != Manifest.permission.READ_EXTERNAL_STORAGE && it != Manifest.permission.WRITE_EXTERNAL_STORAGE
            }
        } else {
            arrayOfPermissions.toList()
        }

        if (newList.isEmpty()) {
            Log.e(TAG, "askPermissions: isEmpty")
            onPermissionGranted()
            return
        }

        requestMultiplePermissionLauncher?.launch(newList.toTypedArray())
    }

    fun askPermission(permission: String) {
        askPermissions(arrayOf(permission))
    }
}