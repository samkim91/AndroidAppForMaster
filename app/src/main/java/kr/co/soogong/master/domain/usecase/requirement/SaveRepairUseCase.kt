package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.data.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class SaveRepairUseCase @Inject constructor(
    private val requirementService: RequirementService,
//    private val requirementDao: RequirementDao,
) {
    operator fun invoke(repairDto: RepairDto): Single<Requirement> {
        return requirementService.saveRepair(repairDto)
            .map { requirementDto ->
                Requirement.fromRequirementDto(requirementDto)
            }
//            .doOnSuccess {
//                requirementDao.insert(it)
//            }
    }
}