package kr.co.soogong.master.domain.usecase.requirement

import kr.co.soogong.master.data.model.requirement.EstimationStatus
import kr.co.soogong.master.data.model.requirement.RequirementCard
import javax.inject.Inject

class GetProgressEstimationListUseCase @Inject constructor(
    private val getEstimationListUseCase: GetEstimationListUseCase
) {
    suspend operator fun invoke(): List<RequirementCard> {
        return getEstimationListUseCase().filter { it.status == EstimationStatus.Progress || it.status == EstimationStatus.CustomDone }
    }
}