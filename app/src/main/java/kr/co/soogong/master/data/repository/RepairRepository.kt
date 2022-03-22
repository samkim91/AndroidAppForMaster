package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.source.network.requirement.repair.RepairService
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import javax.inject.Inject

@Reusable
class RepairRepository @Inject constructor(
    private val repairService: RepairService,
) {

    fun saveRepair(repairDto: RepairDto): Single<RequirementDto> {
        return repairService.saveRepair(repairDto)
    }

    fun getCanceledReasons(groupCodes: List<String>) =
        repairService.getCanceledReasons(groupCodes)

    companion object {
        private const val TAG = "RepairRepository"
    }
}
