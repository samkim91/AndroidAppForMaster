package kr.co.soogong.master.network.mypage

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.data.model.mypage.Notice
import retrofit2.Retrofit
import javax.inject.Inject

class MyPageService @Inject constructor(
    retrofit: Retrofit
) {
    private val myPageInterface = retrofit.create(MyPageInterface::class.java)

    fun getNoticeList(master: String = "free"): Single<List<Notice>> {
        return myPageInterface.getNoticeList(master).map { list ->
            val ret = ArrayList<Notice>()
            for (item in list) {
                ret.add(Notice.fromJson(item))
            }
            return@map ret
        }
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