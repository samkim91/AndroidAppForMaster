package kr.co.soogong.master.domain.usecase.requirement.repair

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.data.repository.RepairRepository
import kr.co.soogong.master.domain.entity.requirement.Requirement
import javax.inject.Inject

@Reusable
class SaveRepairUseCase @Inject constructor(
    private val repairRepository: RepairRepository,
) {
    operator fun invoke(repairDto: RepairDto): Single<Requirement> {
        return repairRepository.saveRepair(repairDto)
            .map { requirementDto ->
                Requirement.fromRequirementDto(requirementDto)
            }
    }
}