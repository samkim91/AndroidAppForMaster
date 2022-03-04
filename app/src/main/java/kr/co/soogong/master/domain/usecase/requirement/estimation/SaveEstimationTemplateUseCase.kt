package kr.co.soogong.master.domain.usecase.requirement.estimation

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.data.repository.EstimationRepository
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import javax.inject.Inject

@Reusable
class SaveEstimationTemplateUseCase @Inject constructor(
    private val estimationRepository: EstimationRepository,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(estimationTemplateDto: EstimationTemplateDto): Single<EstimationTemplateDto> {
        return estimationRepository.saveEstimationTemplate(
            EstimationTemplateDto.createWithMasterId(
                estimationTemplateDto,
                getMasterIdFromSharedUseCase()
            )
        )
    }
}