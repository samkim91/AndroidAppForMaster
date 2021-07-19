@file:JvmName("MultiPartExt")

package kr.co.soogong.master.utility.extension

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.core.net.toFile
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import okio.source

fun Uri.asMultiPart(key: String, contentResolver: ContentResolver): MultipartBody.Part? {
    // Note : 안드로이드 버전에 따라 권한에 차이가 있어서 분기
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // 안드로이드 버전 10, SDK_CODE 29 이상부터는 사진을 따로 빼서 생성
        contentResolver.query(this, null, null, null, null)?.let {
            if(it.moveToNext()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultiPart)?.toMediaType()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultiPart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData(key, displayName, requestBody)
            } else {
                it.close()
                null
            }
        }
    } else {
        // 안드로이드 버전 9, SDK_CODE 28 이하는 사진을 그대로 저장
        val file = this.toFile()
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(key, file.name, requestBody)
    }
}