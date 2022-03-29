package kr.co.soogong.master.data.source.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.requirement.RequirementCardDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
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

    fun getRequirement(requirementId: Int, masterUid: String): Single<ResponseDto<RequirementDto>> {
        return requirementInterface.getRequirement(requirementId, masterUid)
    }
}
