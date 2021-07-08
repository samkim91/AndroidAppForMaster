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

    fun createFile(key: String, uri: Uri): MultipartBody.Part {
        Timber.tag(TAG).d("createFile: $uri")

        val file = File(uri.path!!)

        // TODO: 2021/07/08 need to resize image
//        val outputStream = file.outputStream()
//        val bitmap = if (Build.VERSION.SDK_INT >= 29) {
//            val source = ImageDecoder.createSource(contentResolver, uri)
//            ImageDecoder.decodeBitmap(source)
//        } else {
//            MediaStore.Images.Media.getBitmap(contentResolver, uri)
//        }
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
//        outputStream.flush()
//        outputStream.close()

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(key, file.name, requestBody)
    }

    fun createFiles(
        key: String,
        uriList: List<Uri>
    ): List<MultipartBody.Part> {
        return uriList.map { uri ->
            createFile(key, uri)
        }
    }
}