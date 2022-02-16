package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.data.datasource.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class SaveRepairUseCase @Inject constructor(
    private val requirementService: RequirementService,
//    private val requirementDao: RequirementDao,
) {
    // TODO: 2022/02/16 repository 를 생성자로 가져와서, 작업하도록 변경 필요 !!

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