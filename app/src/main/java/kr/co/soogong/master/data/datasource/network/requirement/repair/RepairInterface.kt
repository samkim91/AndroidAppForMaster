package kr.co.soogong.master.data.datasource.network.requirement.repair

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.CodeDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RepairInterface {

    @POST(HttpContract.SAVE_REPAIR)
    fun saveRepair(@Body repairDto: RepairDto): Single<RequirementDto>

    @GET(HttpContract.GET_CANCELED_REASONS)
    fun getCanceledReasons(
        @Query("groupCodes") groupCodes: List<String>,
    ): Single<List<CodeDto>>
}