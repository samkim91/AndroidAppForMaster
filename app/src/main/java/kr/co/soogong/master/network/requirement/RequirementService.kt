package kr.co.soogong.master.network.requirement

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.data.dto.common.PageableContentDto
import kr.co.soogong.master.data.dto.common.ResponseDto
import kr.co.soogong.master.data.dto.requirement.RequirementCardDto
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class RequirementService @Inject constructor(
    retrofit: Retrofit,
) {
    private val requirementInterface = retrofit.create(RequirementInterface::class.java)

    fun getRequirements(
        masterUid: String,
        status: String,
        readYns: Boolean?,
        offset: Int,
        pageSize: Int,
        order: Int,
    ): Single<ResponseDto<PageableContentDto<RequirementCardDto>>> {
        return requirementInterface.getRequirements(masterUid,
            status,
            readYns,
            offset,
            pageSize,
            order)
    }

    fun searchRequirements(
        masterUid: String,
        searchingText: String,
        searchingPeriod: Int,
        readYns: Boolean?,
        offset: Int,
        pageSize: Int,
        order: Int,
    ): Single<ResponseDto<PageableContentDto<RequirementCardDto>>> {
        return requirementInterface.searchRequirements(
            masterUid,
            searchingText,
            searchingPeriod,
            readYns,
            offset,
            pageSize,
            order,
        )
    }

    fun getRequirement(requirementId: Int, masterUid: String): Single<RequirementDto> {
        return requirementInterface.getRequirement(requirementId, masterUid)
    }

    fun saveEstimation(
        estimationDto: RequestBody,
        measurementImage: List<MultipartBody.Part?>? = null,
    ): Single<EstimationDto> {
        return requirementInterface.saveEstimation(estimationDto, measurementImage)
    }

    fun respondToMeasure(estimationDto: EstimationDto): Single<EstimationDto> {
        return requirementInterface.respondToMeasure(estimationDto)
    }

    fun saveRepair(repairDto: RepairDto): Single<RequirementDto> {
        return requirementInterface.saveRepair(repairDto)
    }

    fun getEstimationTemplates(masterUid: String): Single<List<EstimationTemplateDto>> {
        return requirementInterface.getEstimationTemplates(masterUid)
    }

    fun saveEstimationTemplate(estimationTemplateDto: EstimationTemplateDto): Single<EstimationTemplateDto> {
        return requirementInterface.saveEstimationTemplate(estimationTemplateDto)
    }

    fun deleteEstimationTemplate(id: Int): Single<Boolean> {
        return requirementInterface.deleteEstimationTemplate(id)
    }

    fun getCanceledReasons(groupCodes: List<String>) =
        requirementInterface.getCanceledReasons(groupCodes)

    fun callToClient(estimationId: Int): Single<Boolean> {
        val data = HashMap<String, Any>()
        data["estimationId"] = estimationId
        data["from"] = "Master"

        return requirementInterface.callToClient(data)
    }

    fun requestReview(repairDto: RepairDto): Single<JsonObject> {
        return requirementInterface.requestReview(repairDto)
    }

    fun getCustomerRequests(masterUid: String) = requirementInterface.getCustomerRequests(masterUid)
}
