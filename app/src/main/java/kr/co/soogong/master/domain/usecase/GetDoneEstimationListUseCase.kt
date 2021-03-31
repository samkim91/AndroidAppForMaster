package kr.co.soogong.master.domain.usecase

import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.requirements.RequirementCard
import javax.inject.Inject

class GetDoneEstimationListUseCase @Inject constructor(
    private val getEstimationListUseCase: GetEstimationListUseCase
) {
    suspend operator fun invoke(): List<RequirementCard> {
        return getEstimationListUseCase().filter { it.status == EstimationStatus.Done || it.status == EstimationStatus.Final || it.status == EstimationStatus.Cancel }
    }
}