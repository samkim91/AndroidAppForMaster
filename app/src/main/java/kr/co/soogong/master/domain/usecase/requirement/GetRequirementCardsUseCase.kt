package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Flowable
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.repository.RequirementRepository
import javax.inject.Inject

@Reusable
class GetRequirementCardsUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
) {
    operator fun invoke(
        statusArray: List<String>
    ): Flowable<List<RequirementCard>> {
        return requirementRepository.getRequirementsByStatus(statusArray)
            .map { dtoList ->
                dtoList.map { dto ->
                    RequirementCard.fromRequirementDto(dto)
                }
            }
    }
}