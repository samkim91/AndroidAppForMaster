package kr.co.soogong.master.utility

import android.Manifest
import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kr.co.soogong.master.R
import timber.log.Timber

object PermissionHelper {
    const val TAG = "PermissionHelper"

    fun checkImagePermission(context: Context, onGranted: () -> Unit, onDenied: () -> Unit) {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                Timber.tag(TAG).d("onPermissionGranted: ")
                onGranted()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Timber.tag(TAG).d("onPermissionDenied: ")
                onDenied()
            }
        }

        TedPermission.create()
            .setPermissionListener(permission)
            .setRationaleMessage(context.getString(R.string.request_permission_for_image))
            .setDeniedMessage(context.getString(R.string.permission_denied_status_message))
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            )
            .check()
    }

    fun checkCallPermission(context: Context, onGranted: () -> Unit, onDenied: () -> Unit) {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                Timber.tag(TAG).d("onPermissionGranted: ")
                onGranted()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Timber.tag(TAG).d("onPermissionDenied: ")
                onDenied()
            }
        }

        TedPermission.create()
            .setPermissionListener(permission)
            .setRationaleMessage(context.getString(R.string.request_permission_for_call))
            .setDeniedMessage(context.getString(R.string.permission_denied_status_message))
            .setPermissions(
                Manifest.permission.CALL_PHONE
            )
            .check()
    }

    fun checkLocationPermission(context: Context, onGranted: () -> Unit, onDenied: () -> Unit) {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                Timber.tag(TAG).d("onPermissionGranted: ")
                onGranted()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Timber.tag(TAG).d("onPermissionDenied: ")
                onDenied()
            }
        }

        TedPermission.create()
            .setPermissionListener(permission)
            .setRationaleMessage(context.getString(R.string.request_permission_for_location))
            .setDeniedMessage(context.getString(R.string.permission_denied_status_message))
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            .check()
    }
}