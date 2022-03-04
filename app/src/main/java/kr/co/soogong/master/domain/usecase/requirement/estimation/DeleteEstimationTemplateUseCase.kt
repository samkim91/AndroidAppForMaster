package kr.co.soogong.master.domain.usecase.requirement.estimation

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.repository.EstimationRepository
import javax.inject.Inject

@Reusable
class DeleteEstimationTemplateUseCase @Inject constructor(
    private val estimationRepository: EstimationRepository,
) {
    operator fun invoke(id: Int): Single<Boolean> {
        return estimationRepository.deleteEstimationTemplate(id)
    }
}