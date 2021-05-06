package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EstimationsInterface {
    @GET(HttpContract.GET_ESTIMATION_V2)
    suspend fun getEstimationList(@Path("branch_keycode") branchKeycode: String?): JsonObject

    @GET(HttpContract.GET_ESTIMATION)
    fun getRequirementList(@Query("branch_keycode") auth: String?): Single<List<JsonObject>>

    @GET(HttpContract.ACCEPT_ESTIMATION)
    fun getProgressList(@Query("branch_keycode") auth: String?): Single<List<JsonObject>>

    @POST(HttpContract.REFUSE_ESTIMATION)
    fun refuseToEstimate(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.SEND_ESTIMATION_MESSAGE)
    fun sendMessage(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.CANCEL_ESTIMATE)
    fun cancelEstimate(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.END_ESTIMATE)
    fun endEstimate(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.CALL_TO_CUSTOMER)
    fun callToCustomer(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.ASK_FOR_REVIEW)
    fun askForReview(@Body body: HashMap<String, String?>): Single<Response>
}