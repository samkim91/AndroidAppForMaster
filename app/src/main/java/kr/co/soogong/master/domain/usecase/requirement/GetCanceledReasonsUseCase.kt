package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.CodeDto
import kr.co.soogong.master.data.repository.RequirementRepository
import javax.inject.Inject

@Reusable
class GetCanceledReasonsUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
) {
    operator fun invoke(): Single<List<CodeDto>> {
        return requirementRepository.getCanceledReasons(
            listOf(
                "CanceledReasonOfCommon",
                "CanceledReasonOfMaster"
            )
        )
    }
}