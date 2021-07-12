package kr.co.soogong.master.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import retrofit2.Retrofit
import javax.inject.Inject

class RequirementService @Inject constructor(
    retrofit: Retrofit
) {
    private val requirementInterface = retrofit.create(RequirementInterface::class.java)

    fun getRequirementList(masterUid: String, statusArray: List<String>): Single<List<RequirementDto>> {
        return requirementInterface.getRequirementList(masterUid, statusArray)
    }

    fun getRequirement(requirementId: Int, masterUid: String): Single<RequirementDto> {
        return requirementInterface.getRequirement(requirementId, masterUid)
    }

    fun saveEstimation(estimationDto: EstimationDto): Single<EstimationDto> {
        return requirementInterface.saveEstimation(estimationDto)
    }

    fun saveRepair(repairDto: RepairDto): Single<RequirementDto> {
        return requirementInterface.saveRepair(repairDto)
    }
    fun callToClient(estimationId: Int): Single<Boolean> {
        val data = HashMap<String, Any>()
        data["estimationId"] = estimationId
        data["from"] = "Master"

        return requirementInterface.callToClient(data)
    }

    fun requestReview(repairDto: RepairDto): Single<Boolean> {
        return requirementInterface.requestReview(repairDto)
    }
}
