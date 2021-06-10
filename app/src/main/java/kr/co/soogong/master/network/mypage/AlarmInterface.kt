package kr.co.soogong.master.network.mypage

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import retrofit2.http.Body
import retrofit2.http.POST

interface AlarmInterface {
    @POST(HttpContract.GET_ALARMS)
    fun getAlarmStatus(@Body body: HashMap<String, String?>): Single<JsonObject>

    @POST(HttpContract.SET_ALARMS)
    fun setAlarmStatus(@Body body: HashMap<String, Any?>): Single<JsonObject>
}