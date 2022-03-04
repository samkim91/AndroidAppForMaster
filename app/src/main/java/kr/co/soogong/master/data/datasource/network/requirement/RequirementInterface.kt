package kr.co.soogong.master.data.datasource.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.CodeDto
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.requirement.CustomerRequest
import kr.co.soogong.master.data.entity.requirement.RequirementCardDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.RequirementTotalDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RequirementInterface {

    @GET(HttpContract.REQUIREMENT_TOTAL)
    fun getRequirementTotal(@Query("masterUid") masterUid: String): Single<ResponseDto<RequirementTotalDto>>

    @GET(HttpContract.GET_REQUIREMENTS)
    fun getRequirements(
        @Query("uid") masterUid: String,
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
        @Query("masterUid") masterUid: String,
        @Query("requirementId") requirementId: Int,
    ): Single<RequirementDto>

    @POST(HttpContract.SAVE_REPAIR)
    fun saveRepair(@Body repairDto: RepairDto): Single<RequirementDto>

    @GET(HttpContract.GET_CANCELED_REASONS)
    fun getCanceledReasons(
        @Query("groupCodes") groupCodes: List<String>,
    ): Single<List<CodeDto>>
}