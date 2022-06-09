package kr.co.soogong.master.data.source.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.requirement.RequirementCardDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.RequirementTotalDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequirementInterface {

    @GET(HttpContract.REQUIREMENT_TOTAL)
    fun getRequirementTotal(@Query("masterUid") masterUid: String): Single<ResponseDto<RequirementTotalDto>>

    @GET(HttpContract.GET_REQUIREMENTS)
    fun getRequirements(
        @Path("uid") masterUid: String,
        @Query("status") status: List<String>?,
        @Query("searchingText") searchingText: String?,
        @Query("searchDateRange") searchingPeriod: Int?,
        @Query("readYn") readYn: Boolean?,
        @Query("offset") offset: Int,
        @Query("pageSize") pageSize: Int,
        @Query("order") order: Int,
    ): Single<ResponseDto<PageableContentDto<RequirementCardDto>>>

    @GET(HttpContract.GET_REQUIREMENT)
    fun getRequirement(
        @Path("requirementId") requirementId: Int,
        @Query("masterUid") masterUid: String,
    ): Single<ResponseDto<RequirementDto>>
}