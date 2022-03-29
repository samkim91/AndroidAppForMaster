package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.source.network.requirement.repair.RepairService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@Reusable
class RepairRepository @Inject constructor(
    private val repairService: RepairService,
) {

    fun saveRepair(
        repairDto: RequestBody,
        repairImages: List<MultipartBody.Part?>?,
    ): Single<RequirementDto> {
        return repairService.saveRepair(repairDto, repairImages)
    }

    fun getCanceledReasons(groupCodes: List<String>) =
        repairService.getCanceledReasons(groupCodes)

    companion object {
        private val TAG = RepairRepository::class.java.simpleName
    }
}
