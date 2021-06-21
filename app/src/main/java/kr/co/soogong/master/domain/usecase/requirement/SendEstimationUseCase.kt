package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.network.requirement.RequirementService
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import javax.inject.Inject

@Reusable
class SendEstimationUseCase @Inject constructor(
    private val requirementDao: RequirementDao,
    private val requirementService: RequirementService,
) {
    operator fun invoke(estimationDto: EstimationDto): Single<EstimationDto> {
        return requirementService.sendEstimation(estimationDto)
            .doOnSuccess {
                requirementDao.updateEstimation(requirementId = estimationDto.requirementId, it)
            }
    }
}