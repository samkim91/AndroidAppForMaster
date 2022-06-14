package kr.co.soogong.master.domain.usecase.requirement.estimation

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.estimation.AcceptingMeasureDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.repository.EstimationRepository
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import javax.inject.Inject

@Reusable
class AcceptToMeasureUseCase @Inject constructor(
    private val estimationRepository: EstimationRepository,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(acceptingMeasureDto: AcceptingMeasureDto): Single<EstimationDto> {
        return estimationRepository.acceptToMeasure(acceptingMeasureDto.copy(
            masterId = getMasterIdFromSharedUseCase()
        ))
    }
}