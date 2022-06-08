package kr.co.soogong.master.domain.usecase.requirement.estimation

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.estimation.RefusingMeasureDto
import kr.co.soogong.master.data.repository.EstimationRepository
import javax.inject.Inject

@Reusable
class RefuseToMeasureUseCase @Inject constructor(
    private val estimationRepository: EstimationRepository,
) {
    operator fun invoke(refusingMeasureDto: RefusingMeasureDto): Single<EstimationDto> {
        return estimationRepository.refuseToMeasure(refusingMeasureDto)
    }
}