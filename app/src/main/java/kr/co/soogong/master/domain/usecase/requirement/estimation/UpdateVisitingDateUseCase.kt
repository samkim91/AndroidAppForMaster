package kr.co.soogong.master.domain.usecase.requirement.estimation

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.entity.requirement.estimation.VisitingDateUpdateDto
import kr.co.soogong.master.data.repository.EstimationRepository
import javax.inject.Inject

@Reusable
class UpdateVisitingDateUseCase @Inject constructor(
    private val estimationRepository: EstimationRepository,
) {
    suspend operator fun invoke(estimationToken: String, visitingDateUpdateDto: VisitingDateUpdateDto) {
        return withContext(Dispatchers.IO) {
            estimationRepository.updateVisitingDate(estimationToken, visitingDateUpdateDto)
        }
    }
}