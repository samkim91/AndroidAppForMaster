package kr.co.soogong.master.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RequirementInterface {
    @GET(HttpContract.GET_REQUIREMENT_LIST)
    fun getRequirementList(@Query("masterId") masterId : Int, @Query("statusArray") statusArray: List<String>): Single<List<RequirementDto>>

    @GET(HttpContract.GET_REQUIREMENT)
    fun getRequirement(@Query("requirementId") requirementId : Int, @Query("masterId") masterId: Int): Single<RequirementDto>

    @POST(HttpContract.SAVE_ESTIMATION)
    fun saveEstimation(@Body body: EstimationDto): Single<EstimationDto>

    @POST(HttpContract.SAVE_REPAIR)
    fun saveRepair(@Body repairDto: RepairDto): Single<RequirementDto>





    @POST(HttpContract.CALL_TO_CUSTOMER)
    fun callToCustomer(@Body body: HashMap<String, Any>): Single<Response>

    @POST(HttpContract.ASK_FOR_REVIEW)
    fun askForReview(@Body body: HashMap<String, Any?>): Single<Response>
}