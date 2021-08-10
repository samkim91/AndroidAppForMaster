package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.repository.RequirementRepository
import javax.inject.Inject

@Reusable
class GetRequirementCardsFromLocalUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
) {
    operator fun invoke(
        statusArray: List<String>,
        canceledYn: Boolean = false
    ): Single<List<RequirementCard>> {
        return requirementRepository.getRequirementsFromLocal(statusArray, canceledYn)
            .firstOrError()
            .map { dtoList ->
                dtoList.map { dto ->
                    RequirementCard.fromRequirementDto(dto)
                }
            }
    }
}