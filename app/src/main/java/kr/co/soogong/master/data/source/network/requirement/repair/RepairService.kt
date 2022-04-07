package kr.co.soogong.master.data.source.network.requirement.repair

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class RepairService @Inject constructor(
    retrofit: Retrofit,
) {

    private val repairInterface = retrofit.create(RepairInterface::class.java)

    suspend fun saveRepair(
        repairDto: RequestBody,
        repairImages: List<MultipartBody.Part?>?,
    ) {
        return repairInterface.saveRepair(repairDto, repairImages)
    }

    suspend fun cancelRepair(
        cancelRepairDto: RequestBody,
    ) {
        return repairInterface.cancelRepair(cancelRepairDto)
    }

    fun getCanceledReasons(groupCodes: List<String>) =
        repairInterface.getCanceledReasons(groupCodes)
}
