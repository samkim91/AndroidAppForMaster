package kr.co.soogong.master.network.requirement

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.dto.requirement.search.SearchDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class RequirementService @Inject constructor(
    retrofit: Retrofit
) {
    private val requirementInterface = retrofit.create(RequirementInterface::class.java)

    fun getRequirementsByStatus(masterUid: String, statusArray: List<String>): Single<List<RequirementDto>> {
        return requirementInterface.getRequirementsByStatus(masterUid, statusArray)
    }

    fun searchRequirements(masterUid: String, searchingText: String, searchingPeriod: Int): Single<List<RequirementDto>> {
        return requirementInterface.searchRequirements(masterUid, searchingText, searchingPeriod)
    }

    fun getRequirement(requirementId: Int, masterUid: String): Single<RequirementDto> {
        return requirementInterface.getRequirement(requirementId, masterUid)
    }

    fun saveEstimation(estimationDto: RequestBody, measurementImage: MultipartBody.Part? = null): Single<EstimationDto> {
        return requirementInterface.saveEstimation(estimationDto, measurementImage)
    }

    fun respondToMeasure(estimationDto: EstimationDto): Single<EstimationDto> {
        return requirementInterface.respondToMeasure(estimationDto)
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

    fun requestReview(repairDto: RepairDto): Single<JsonObject> {
        return requirementInterface.requestReview(repairDto)
    }
}
