package kr.co.soogong.master.utility

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import kr.co.soogong.master.utility.extension.asMultiPart
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber

object MultipartGenerator {
    const val TAG = "MultipartGenerator"

    fun createJson(anyDto: Any): RequestBody {
        val jsonDto = Gson().toJson(anyDto)
        Timber.tag(TAG).d("createJson: $jsonDto")

        return jsonDto.toRequestBody("application/json".toMediaTypeOrNull())
    }

    fun createFile(context: Context, key: String, uri: Uri?): MultipartBody.Part? {
        Timber.tag(TAG).d("createFile: $uri to $key")
        return if (uri == null || uri == Uri.EMPTY || uri.toString().contains("https://")) null
        else uri.asMultiPart(key, context.contentResolver)
    }

    fun createFiles(
        context: Context,
        key: String,
        uriList: List<Uri?>?
    ): List<MultipartBody.Part?>? {
        return uriList?.map { uri ->
            createFile(context, key, uri)
        }
    }

}