package kr.co.soogong.master.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import retrofit2.Retrofit
import javax.inject.Inject

class RequirementService @Inject constructor(
    retrofit: Retrofit
) {
    private val requirementInterface = retrofit.create(RequirementInterface::class.java)

    fun getRequirementList(masterId: Int, statusArray: List<String>): Single<List<RequirementDto>> {
        return requirementInterface.getRequirementList(masterId, statusArray)
    }

    fun getRequirement(requirementId: Int, masterId: Int): Single<RequirementDto> {
        return requirementInterface.getRequirement(requirementId, masterId)
    }

    fun saveEstimation(estimationDto: EstimationDto): Single<EstimationDto> {
        return requirementInterface.saveEstimation(estimationDto)
    }

    fun saveRepair(repairDto: RepairDto): Single<RequirementDto> {
        return requirementInterface.saveRepair(repairDto)
    }

    fun callToCustomer(
        id: Int,
    ): Single<Response> {
        val data = HashMap<String, Any>()
        data["keycode"] = id
        return requirementInterface.callToCustomer(data)
    }

    fun askForReview(
        id: Int,
        branchKeycode: String?
    ): Single<Response> {
        val data = HashMap<String, Any?>()
        data["keycode"] = id
        data["branch_keycode"] = branchKeycode
        return requirementInterface.askForReview(data)
    }
}