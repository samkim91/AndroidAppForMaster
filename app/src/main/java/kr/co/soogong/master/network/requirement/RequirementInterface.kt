package kr.co.soogong.master.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RequirementInterface {
    @GET(HttpContract.GET_REQUIREMENT_LIST)
    fun getRequirementList(@Query("masterId") masterId : Int, @Query("statusArray") statusArray: List<String>): Single<List<RequirementDto>>

    @GET(HttpContract.GET_REQUIREMENT)
    fun getRequirement(@Query("requirementId") requirementId : Int, @Query("masterId") masterId: Int): Single<RequirementDto>

    @POST(HttpContract.SEND_ESTIMATION)
    fun sendEstimation(@Body body: EstimationDto): Single<EstimationDto>


    @POST(HttpContract.CANCEL_ESTIMATE)
    fun cancelEstimate(@Body body: HashMap<String, Any?>): Single<Response>

    @POST(HttpContract.END_ESTIMATE)
    fun endEstimate(@Body body: HashMap<String, Any?>): Single<Response>

    @POST(HttpContract.CALL_TO_CUSTOMER)
    fun callToCustomer(@Body body: HashMap<String, Any>): Single<Response>

    @POST(HttpContract.ASK_FOR_REVIEW)
    fun askForReview(@Body body: HashMap<String, Any?>): Single<Response>
}