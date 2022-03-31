package kr.co.soogong.master.data.source.network.requirement.repair

import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class RepairService @Inject constructor(
    retrofit: Retrofit,
) {

    private val repairInterface = retrofit.create(RepairInterface::class.java)

    fun saveRepair(
        repairDto: RequestBody,
        repairImages: List<MultipartBody.Part?>?,
    ): Single<RequirementDto> {
        return repairInterface.saveRepair(repairDto, repairImages)
    }

    fun getCanceledReasons(groupCodes: List<String>) =
        repairInterface.getCanceledReasons(groupCodes)
}
