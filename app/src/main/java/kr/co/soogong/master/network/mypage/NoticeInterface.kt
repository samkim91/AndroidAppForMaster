package kr.co.soogong.master.network.mypage

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeInterface {
    @GET(HttpContract.GET_NOTICE)
    fun getNoticeList(@Query("for") master: String): Single<List<JsonObject>>
}