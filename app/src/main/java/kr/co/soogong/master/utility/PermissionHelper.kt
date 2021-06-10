package kr.co.soogong.master.utility

import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
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

        TedPermission.with(context)
            .setPermissionListener(permission)
            .setRationaleMessage(context.getString(R.string.request_permission_for_image))
            .setDeniedMessage(context.getString(R.string.permission_denied_status_message))
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)
            .check()
    }
}