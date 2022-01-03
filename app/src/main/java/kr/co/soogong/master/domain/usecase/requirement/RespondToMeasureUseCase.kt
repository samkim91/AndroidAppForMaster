package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class RespondToMeasureUseCase @Inject constructor(
    private val requirementDao: RequirementDao,
    private val requirementService: RequirementService,
) {
    operator fun invoke(estimationDto: EstimationDto): Single<EstimationDto> {
        return requirementService.respondToMeasure(estimationDto)
//            .doOnSuccess {
//                requirementDao.updateEstimation(requirementId = it.requirementId, it)
//            }
    }
}