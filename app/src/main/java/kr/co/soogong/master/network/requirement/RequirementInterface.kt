package kr.co.soogong.master.network.requirement

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.dto.requirement.search.SearchDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RequirementInterface {
    @GET(HttpContract.GET_REQUIREMENT_LIST_BY_UID)
    fun getRequirementsByStatus(@Query("masterUid") masterUid : String, @Query("statusArray") statusArray: List<String>): Single<List<RequirementDto>>

    @GET(HttpContract.SEARCH_REQUIREMENTS)
    fun searchRequirements(@Query("masterUid") masterUid : String, @Query("search") searchingText: String, @Query("interval") searchingPeriod: Int): Single<List<RequirementDto>>

    @GET(HttpContract.GET_REQUIREMENT)
    fun getRequirement(@Query("requirementId") requirementId : Int, @Query("masterUid") masterUid: String): Single<RequirementDto>

    @Multipart
    @POST(HttpContract.SAVE_ESTIMATION)
    fun saveEstimation(@Part("estimationDto") estimationDto: RequestBody, @Part measurementImage: MultipartBody.Part?): Single<EstimationDto>

    @POST(HttpContract.RESPOND_TO_MEASURE)
    fun respondToMeasure(@Body estimationDto: EstimationDto): Single<EstimationDto>

    @POST(HttpContract.SAVE_REPAIR)
    fun saveRepair(@Body repairDto: RepairDto): Single<RequirementDto>

    @POST(HttpContract.CALL_TO_CLIENT)
    fun callToClient(@Body data: HashMap<String, Any>): Single<Boolean>

    @POST(HttpContract.REQUEST_REVIEW)
    fun requestReview(@Body repairDto: RepairDto): Single<JsonObject>

}