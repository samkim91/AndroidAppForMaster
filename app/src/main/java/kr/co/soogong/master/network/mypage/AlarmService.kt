package kr.co.soogong.master.network.mypage

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class AlarmService @Inject constructor(
    retrofit: Retrofit
) {
    private val alarmInterface = retrofit.create(AlarmInterface::class.java)

    fun getAlarmStatus(keycode: String?): Single<HashMap<String, Boolean>> {
        val data = HashMap<String, String?>()
        data["keycode"] = keycode

        return alarmInterface.getAlarmStatus(data).map { req ->
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

        return alarmInterface.setAlarmStatus(data).map {
            return@map it
        }
    }
}