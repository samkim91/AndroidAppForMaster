package kr.co.soogong.master.data.datasource.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.requirement.RequirementCardDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class RequirementService @Inject constructor(
    retrofit: Retrofit,
) {
    private val requirementInterface = retrofit.create(RequirementInterface::class.java)

    fun getRequirementTotal(masterUid: String) = requirementInterface.getRequirementTotal(masterUid)

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

    fun getRequirement(masterUid: String, requirementId: Int): Single<RequirementDto> {
        return requirementInterface.getRequirement(masterUid, requirementId)
    }

    fun saveRepair(repairDto: RepairDto): Single<RequirementDto> {
        return requirementInterface.saveRepair(repairDto)
    }

    fun getCanceledReasons(groupCodes: List<String>) =
        requirementInterface.getCanceledReasons(groupCodes)
}
