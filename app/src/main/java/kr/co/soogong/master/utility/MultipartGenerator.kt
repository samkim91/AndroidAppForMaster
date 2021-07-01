package kr.co.soogong.master.utility

import android.net.Uri
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File

object MultipartGenerator {
    const val TAG = "MultipartGenerator"

    fun createJson(anyDto: Any): RequestBody {
        val jsonDto = Gson().toJson(anyDto)
        Timber.tag(TAG).d("createJson: $jsonDto")

        return jsonDto.toRequestBody("application/json".toMediaTypeOrNull())
    }

    fun createFile(uri: Uri): MultipartBody.Part {
        Timber.tag(TAG).d("createFile: $uri")

        val file = File(uri.path)
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("attachments", file.name, requestBody)
    }

    fun createFiles(uriList: List<Uri>): List<MultipartBody.Part> {
        return uriList.map { uri ->
            createFile(uri)
        }
    }
}