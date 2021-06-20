package kr.co.soogong.master.domain.usecase.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.RequirementCard
import javax.inject.Inject

class GetProgressEstimationListUseCase @Inject constructor(
    private val getRequirementListUseCase: GetRequirementListUseCase,
) {
    operator fun invoke(): Single<List<RequirementCard>> {
        return getRequirementListUseCase(listOf("Repairing", "RequestFinish"))
    }
}