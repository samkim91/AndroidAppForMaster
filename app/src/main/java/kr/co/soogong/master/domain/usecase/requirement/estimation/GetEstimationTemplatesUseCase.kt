package kr.co.soogong.master.domain.usecase.requirement.estimation

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.data.datasource.network.requirement.RequirementService
import kr.co.soogong.master.data.repository.EstimationRepository
import javax.inject.Inject

@Reusable
class GetEstimationTemplatesUseCase @Inject constructor(
    private val estimationRepository: EstimationRepository,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(): Single<List<EstimationTemplateDto>> {
        return estimationRepository.getEstimationTemplates(getMasterUidFromSharedUseCase())
    }
}