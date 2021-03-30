package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.requirements.RequirementCard
import javax.inject.Inject

@Reusable
class GetReceivedEstimationListUseCase @Inject constructor(
    private val getEstimationListUseCase: GetEstimationListUseCase
) {
    suspend operator fun invoke(): List<RequirementCard> {
        return getEstimationListUseCase().filter { it.status == EstimationStatus.Request || it.status == EstimationStatus.Waiting }
    }
}