package kr.co.soogong.master.domain.usecase.requirement.estimation

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.repository.EstimationRepository
import javax.inject.Inject

@Reusable
class RespondToMeasureUseCase @Inject constructor(
    private val estimationRepository: EstimationRepository,
) {
    operator fun invoke(estimationDto: EstimationDto): Single<EstimationDto> {
        return estimationRepository.respondToMeasure(estimationDto)
    }
}