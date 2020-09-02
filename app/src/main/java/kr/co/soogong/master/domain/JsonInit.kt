package kr.co.soogong.master.domain

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.requirements.Requirement
import timber.log.Timber
import java.io.IOException
import java.io.InputStream

object JsonInit {
    private const val TAG = "JsonInit"

    fun loadActionFlowList(context: Context): List<Requirement>? {
        val jsonString: String?

        jsonString = try {
            val inputStream: InputStream = context.assets.open("dummy_test.json")
            inputStream.bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            Timber.e(ioException)
            null
        }

        return Gson().fromJson<ArrayList<Requirement>?>(
            jsonString,
            object : TypeToken<ArrayList<Requirement>>() {}.type
        )
    }
}