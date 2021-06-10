package kr.co.soogong.master.network.mypage

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyPageInterface {
    @GET(HttpContract.GET_NOTICE)
    fun getNoticeList(@Query("for") master: String): Single<List<JsonObject>>

    @POST(HttpContract.GET_ALARMS)
    fun getAlarmStatus(@Body body: HashMap<String, String?>): Single<JsonObject>

    @POST(HttpContract.SET_ALARMS)
    fun setAlarmStatus(@Body body: HashMap<String, Any?>): Single<JsonObject>
}