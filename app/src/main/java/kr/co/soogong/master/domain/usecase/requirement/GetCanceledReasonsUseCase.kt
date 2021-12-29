package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.common.CodeDto
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetCanceledReasonsUseCase @Inject constructor(
    private val requirementService: RequirementService,
) {
    operator fun invoke(): Single<List<CodeDto>> {
        return requirementService.getCanceledReasons(listOf("CanceledReasonOfCommon",
            "CanceledReasonOfMaster"))
    }
}