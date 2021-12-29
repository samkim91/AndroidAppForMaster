package kr.co.soogong.master.network.requirement

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.common.CodeDto
import kr.co.soogong.master.data.dto.common.PageableContentDto
import kr.co.soogong.master.data.dto.common.ResponseDto
import kr.co.soogong.master.data.dto.requirement.CustomerRequest
import kr.co.soogong.master.data.dto.requirement.RequirementCardDto
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RequirementInterface {
    @GET(HttpContract.GET_REQUIREMENTS)
    fun getRequirements(
        @Query("masterUid") masterUid: String,
        @Query("status") status: String,
        @Query("readYns") readYns: Boolean?,
        @Query("offset") offset: Int,
        @Query("pageSize") pageSize: Int,
        @Query("order") order: Int,
    ): Single<ResponseDto<PageableContentDto<RequirementCardDto>>>

    @GET(HttpContract.SEARCH_REQUIREMENTS)
    fun searchRequirements(
        @Query("masterUid") masterUid: String,
        @Query("search") searchingText: String,
        @Query("interval") searchingPeriod: Int,
        @Query("readYns") readYns: Boolean?,
        @Query("offset") offset: Int,
        @Query("pageSize") pageSize: Int,
        @Query("order") order: Int,
    ): Single<ResponseDto<PageableContentDto<RequirementCardDto>>>

    @GET(HttpContract.GET_REQUIREMENT)
    fun getRequirement(
        @Query("requirementId") requirementId: Int,
        @Query("masterUid") masterUid: String,
    ): Single<RequirementDto>

    @Multipart
    @POST(HttpContract.SAVE_ESTIMATION)
    fun saveEstimation(
        @Part("estimationDto") estimationDto: RequestBody,
        @Part measurementImage: List<MultipartBody.Part?>?,
    ): Single<EstimationDto>

    @POST(HttpContract.RESPOND_TO_MEASURE)
    fun respondToMeasure(@Body estimationDto: EstimationDto): Single<EstimationDto>

    @POST(HttpContract.SAVE_REPAIR)
    fun saveRepair(@Body repairDto: RepairDto): Single<RequirementDto>

    @GET(HttpContract.GET_ESTIMATION_TEMPLATES)
    fun getEstimationTemplates(@Query("uid") masterUid: String): Single<List<EstimationTemplateDto>>

    @Multipart
    @POST(HttpContract.SAVE_ESTIMATION_TEMPLATE)
    fun saveEstimationTemplate(@Part("estimationTemplateDto") estimationTemplateDto: EstimationTemplateDto): Single<EstimationTemplateDto>

    @DELETE(HttpContract.DELETE_ESTIMATION_TEMPLATE)
    fun deleteEstimationTemplate(@Path("id") id: Int): Single<Boolean>

    @GET(HttpContract.GET_CANCELED_REASONS)
    fun getCanceledReasons(
        @Query("groupCodes") groupCodes: List<String>,
    ): Single<List<CodeDto>>

    @POST(HttpContract.CALL_TO_CLIENT)
    fun callToClient(@Body data: HashMap<String, Any>): Single<Boolean>

    @POST(HttpContract.REQUEST_REVIEW)
    fun requestReview(@Body repairDto: RepairDto): Single<JsonObject>

    @GET(HttpContract.GET_CUSTOMER_REQUESTS)
    fun getCustomerRequests(
        @Query("uid") masterUid: String,
    ): Single<CustomerRequest>
}