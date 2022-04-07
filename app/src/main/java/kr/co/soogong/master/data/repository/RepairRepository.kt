package kr.co.soogong.master.data.repository

import dagger.Reusable
import kr.co.soogong.master.data.source.network.requirement.repair.RepairService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@Reusable
class RepairRepository @Inject constructor(
    private val repairService: RepairService,
) {

    suspend fun saveRepair(
        repairDto: RequestBody,
        repairImages: List<MultipartBody.Part?>?,
    ) {
        return repairService.saveRepair(repairDto, repairImages)
    }

    suspend fun cancelRepair(
        cancelRepairDto: RequestBody,
    ) {
        return repairService.cancelRepair(cancelRepairDto)
    }

    fun getCanceledReasons(groupCodes: List<String>) =
        repairService.getCanceledReasons(groupCodes)

    companion object {
        private val TAG = RepairRepository::class.java.simpleName
    }
}
