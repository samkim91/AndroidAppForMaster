package kr.co.soogong.master.domain.usecase.requirement.estimation

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.repository.EstimationRepository
import javax.inject.Inject

@Reusable
class IncreaseCallCountUseCase @Inject constructor(
    private val estimationRepository: EstimationRepository,
) {
    operator fun invoke(estimationId: Int): Single<Boolean> {
        val data = HashMap<String, Any>()
        data["estimationId"] = estimationId
        data["from"] = "Master"

        return estimationRepository.increaseCallCount(data)
    }
}