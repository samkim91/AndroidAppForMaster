package kr.co.soogong.master.data.datasource.network.preferences

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.VersionDto
import kr.co.soogong.master.data.entity.preferences.NoticeDto
import retrofit2.Retrofit
import javax.inject.Inject

class PreferencesService @Inject constructor(
    retrofit: Retrofit,
) {
    private val preferencesInterface = retrofit.create(PreferencesInterface::class.java)

    fun getNotices(sections: List<String>): Single<List<NoticeDto>> {
        return preferencesInterface.getNotices(sections)
    }

    fun getNotice(id: Int): Single<NoticeDto> {
        return preferencesInterface.getNotice(id)
    }

    fun getAlarmStatus(keycode: String?): Single<HashMap<String, Boolean>> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode

        return preferencesInterface.getAlarmStatus(data).map { req ->
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

        return preferencesInterface.setAlarmStatus(data).map {
            return@map it
        }
    }

    fun getVersion(): Single<VersionDto> =
        preferencesInterface.getVersion()
}