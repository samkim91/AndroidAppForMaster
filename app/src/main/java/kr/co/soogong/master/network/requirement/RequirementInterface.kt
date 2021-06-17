package kr.co.soogong.master.network.requirement

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import retrofit2.http.*

interface RequirementInterface {
    @GET(HttpContract.GET_REQUIREMENT_LIST)
    fun getRequirementList(@Query("masterId") masterId : Int, @Query("statusArray") statusArray: List<String>): Single<List<RequirementDto>>

    @GET(HttpContract.GET_REQUIREMENT)
    fun getRequirement(@Query("id") id : Int): Single<RequirementDto>






    @POST(HttpContract.REFUSE_ESTIMATION)
    fun refuseToEstimate(@Body body: HashMap<String, Any?>): Single<Response>

    @POST(HttpContract.SEND_ESTIMATION_MESSAGE)
    fun sendMessage(@Body body: HashMap<String, String?>): Single<Response>

    @POST(HttpContract.CANCEL_ESTIMATE)
    fun cancelEstimate(@Body body: HashMap<String, Any?>): Single<Response>

    @POST(HttpContract.END_ESTIMATE)
    fun endEstimate(@Body body: HashMap<String, Any?>): Single<Response>

    @POST(HttpContract.CALL_TO_CUSTOMER)
    fun callToCustomer(@Body body: HashMap<String, Any>): Single<Response>

    @POST(HttpContract.ASK_FOR_REVIEW)
    fun askForReview(@Body body: HashMap<String, Any?>): Single<Response>
}