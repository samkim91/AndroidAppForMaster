package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class SaveEstimationTemplateUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
) {
    operator fun invoke(estimationTemplateDto: EstimationTemplateDto): Single<EstimationTemplateDto> {
        return requirementService.saveEstimationTemplate(EstimationTemplateDto.addMasterId(
            estimationTemplateDto,
            getMasterIdFromSharedUseCase()))
    }
}