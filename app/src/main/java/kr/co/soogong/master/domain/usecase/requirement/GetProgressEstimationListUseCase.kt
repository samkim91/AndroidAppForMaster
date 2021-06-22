package kr.co.soogong.master.domain.usecase.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import javax.inject.Inject

class GetProgressEstimationListUseCase @Inject constructor(
    private val getRequirementListUseCase: GetRequirementListUseCase,
) {
    operator fun invoke(): Single<List<RequirementCard>> {
        return getRequirementListUseCase(listOf(RequirementStatus.Repairing.toCode(), RequirementStatus.RequestFinish.toCode()))
    }
}