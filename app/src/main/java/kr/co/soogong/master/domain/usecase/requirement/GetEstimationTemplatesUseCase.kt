package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.estimationTemplate.EstimationTemplateDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.data.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetEstimationTemplatesUseCase @Inject constructor(
    private val requirementService: RequirementService,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(): Single<List<EstimationTemplateDto>> {
        return requirementService.getEstimationTemplates(getMasterUidFromSharedUseCase()!!)
    }
}