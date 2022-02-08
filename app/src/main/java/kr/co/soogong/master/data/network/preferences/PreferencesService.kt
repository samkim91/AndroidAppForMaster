package kr.co.soogong.master.data.network.preferences

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.data.entity.preferences.NoticeDto
import retrofit2.Retrofit
import javax.inject.Inject

class PreferencesService @Inject constructor(
    retrofit: Retrofit,
) {
    private val myPageInterface = retrofit.create(PreferencesInterface::class.java)

    fun getNotices(sections: List<String>): Single<List<NoticeDto>> {
        return myPageInterface.getNotices(sections)
    }

    fun getNotice(id: Int): Single<NoticeDto> {
        return myPageInterface.getNotice(id)
    }

    fun getAlarmStatus(keycode: String?): Single<HashMap<String, Boolean>> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode

        return myPageInterface.getAlarmStatus(data).map { req ->
            val map = HashMap<String, Boolean>()
            if (req.get("status").asInt == 200) {
                val array = req.get("data").asJsonArray
                for (item in array) {
                    map[item.asJsonObject.get("variable_type").asString] =
                        item.asJsonObject.get("value").asBoolean
                }
            }
            return@map map
        }
    }

    fun setAlarmStatus(keycode: String?, type: String, value: Boolean): Single<JsonObject> {
        val data = HashMap<String, Any?>()
        data["keycode"] = keycode
        data["type"] = type
        data["value"] = value

        return myPageInterface.setAlarmStatus(data).map {
            return@map it
        }
    }
}