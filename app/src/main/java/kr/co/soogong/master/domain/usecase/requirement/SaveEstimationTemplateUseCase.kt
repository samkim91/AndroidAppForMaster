package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.datasource.network.requirement.RequirementService
import kr.co.soogong.master.data.entity.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.data.repository.ProfileRepository
import javax.inject.Inject

@Reusable
class SaveEstimationTemplateUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val profileRepository: ProfileRepository,
) {
    // TODO: 2022/02/16 repository 를 생성자로 가져와서, 작업하도록 변경 필요 !!

    operator fun invoke(estimationTemplateDto: EstimationTemplateDto): Single<EstimationTemplateDto> {
        return requirementService.saveEstimationTemplate(EstimationTemplateDto.addMasterId(
            estimationTemplateDto,
            profileRepository.getMasterIdFromShared()))
    }
}