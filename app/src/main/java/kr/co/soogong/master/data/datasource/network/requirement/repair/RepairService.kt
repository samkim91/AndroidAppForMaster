package kr.co.soogong.master.data.datasource.network.requirement.repair

import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import retrofit2.Retrofit
import javax.inject.Inject

class RepairService @Inject constructor(
    retrofit: Retrofit,
) {

    private val repairInterface = retrofit.create(RepairInterface::class.java)

    fun saveRepair(repairDto: RepairDto): Single<RequirementDto> {
        return repairInterface.saveRepair(repairDto)
    }

    fun getCanceledReasons(groupCodes: List<String>) =
        repairInterface.getCanceledReasons(groupCodes)
}
