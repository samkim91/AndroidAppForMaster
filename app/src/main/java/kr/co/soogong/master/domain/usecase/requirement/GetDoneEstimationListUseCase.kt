package kr.co.soogong.master.domain.usecase.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import javax.inject.Inject

class GetDoneEstimationListUseCase @Inject constructor(
    private val getRequirementListUseCase: GetRequirementListUseCase,
    private val getRequirementListFromLocalUseCase: GetRequirementListFromLocalUseCase,
) {
    operator fun invoke(statusArray: List<String>, canceledYn: Boolean = false): Single<List<RequirementCard>> {
        if(canceledYn) {         // canceled requirement 를 가져옴.
            return getRequirementListFromLocalUseCase(statusArray, canceledYn)
        }

        return if(statusArray.size == 1) {         // 필터를 사용하는 경우에는 로컬에서 가져옴.
            getRequirementListFromLocalUseCase(statusArray)
        } else {
            getRequirementListUseCase(statusArray)
        }
    }
}