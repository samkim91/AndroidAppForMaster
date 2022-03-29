package kr.co.soogong.master.data.source.network.requirement.repair

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.CodeDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RepairInterface {

    @Multipart
    @POST(HttpContract.SAVE_REPAIR)
    fun saveRepair(
        @Part("repairDto") repairDto: RequestBody,
        @Part repairImages: List<MultipartBody.Part?>?,
    ): Single<RequirementDto>

    @GET(HttpContract.GET_CANCELED_REASONS)
    fun getCanceledReasons(
        @Query("groupCodes") groupCodes: List<String>,
    ): Single<List<CodeDto>>
}