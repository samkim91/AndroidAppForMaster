package kr.co.soogong.master.utility

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

    fun createFile(path: String): MultipartBody.Part {
        Timber.tag(TAG).d("createFile: $path")

        val file = File(path)
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("attachments", file.name, requestBody)
    }

    fun createFiles(pathList: List<String>): List<MultipartBody.Part> {
        return pathList.map { path ->
            createFile(path)
        }
    }
}