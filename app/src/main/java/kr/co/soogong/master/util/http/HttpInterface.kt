package kr.co.soogong.master.util.http

import io.reactivex.Completable
import io.reactivex.Single
import kr.co.soogong.master.data.rawtype.RawRequirementItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HttpInterface {
    @GET("api/v1/transmissions/list")
    fun getRequirementList(@Query("branch_keycode") auth: String): Single<List<RawRequirementItem>>

    @POST("api/v1/transmissions/refuse")
    fun refuseRequirement(@Body body: HashMap<String, String>): Completable

    @POST("api/v1/transmissions/send_message")
    fun sendMessage(@Body body: HashMap<String, String>): Single<String>
}