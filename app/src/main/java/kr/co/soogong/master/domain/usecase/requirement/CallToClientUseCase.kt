package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class CallToClientUseCase @Inject constructor(
    private val requirementService: RequirementService,
) {
    operator fun invoke(estimationId: Int): Single<Boolean> {
        return requirementService.callToClient(estimationId)
    }
}