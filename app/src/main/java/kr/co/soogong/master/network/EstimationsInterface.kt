package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EstimationsInterface {
    @GET(HttpContract.GET_ESTIMATION_V2)
    fun getEstimationList(@Path("branch_keycode") branchKeycode: String?): Single<JsonObject>

    @GET(HttpContract.GET_ESTIMATION)
    fun getRequirementList(@Query("branch_keycode") auth: String?): Single<List<JsonObject>>

    @GET(HttpContract.GET_ESTIMATION_ACCEPT)
    fun getProgressList(@Query("branch_keycode") auth: String?): Single<List<JsonObject>>

    @POST(HttpContract.GET_ESTIMATION_REFUSE)
    fun refuseRequirement(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.SEND_ESTIMATION_MESSAGE)
    fun sendMessage(@Body body: HashMap<String, String?>): Single<String>
}