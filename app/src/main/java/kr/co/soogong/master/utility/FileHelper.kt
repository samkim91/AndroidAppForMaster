package kr.co.soogong.master.utility

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import timber.log.Timber

object FileHelper {
    private const val TAG = "FileHelper"

    fun isImageExtension(list: List<Uri>, context: Context): Boolean? {
        list.map { uri ->
            return isImageExtension(uri, context)
        }
        return true
    }

    fun isImageExtension(uri: Uri, context: Context): Boolean? {
        Timber.tag(TAG).i("isImageExtension: $uri")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.query(uri, null, null, null, null)?.let {
                if (it.moveToNext()) {
                    val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    it.close()
                    listOf(".jpg", ".jpeg", ".png").contains(displayName.substring(displayName.lastIndexOf(".")))
                } else {
                    it.close()
                    false
                }
            }
        } else {
            listOf(".jpg", ".jpeg", ".png").contains(
                uri.toString().substring(uri.toString().lastIndexOf("."))
            )
        }
    }
}