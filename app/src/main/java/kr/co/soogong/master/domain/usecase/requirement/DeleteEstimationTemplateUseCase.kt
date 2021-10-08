package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class DeleteEstimationTemplateUseCase @Inject constructor(
    private val requirementService: RequirementService,
) {
    operator fun invoke(id: Int): Single<Boolean> {
        return requirementService.deleteEstimationTemplate(id)
    }
}