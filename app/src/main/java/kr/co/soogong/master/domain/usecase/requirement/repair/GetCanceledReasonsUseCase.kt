package kr.co.soogong.master.domain.usecase.requirement.repair

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.CodeDto
import kr.co.soogong.master.data.repository.RepairRepository
import javax.inject.Inject

@Reusable
class GetCanceledReasonsUseCase @Inject constructor(
    private val repairRepository: RepairRepository,
) {
    operator fun invoke(): Single<List<CodeDto>> {
        return repairRepository.getCanceledReasons(
            listOf(
                "CanceledReasonOfCommon",
                "CanceledReasonOfMaster"
            )
        )
    }
}