package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.repository.RequirementRepository
import javax.inject.Inject

@Reusable
class SearchRequirementCardsUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
) {
    operator fun invoke(
        searchingText: String,
        searchingPeriod: Int,
    ): Single<List<RequirementCard>> {
        return requirementRepository.searchRequirementsFromServer(searchingText, searchingPeriod)
            .map { dtoList ->
                dtoList.map { dto ->
                    RequirementCard.fromRequirementDto(dto)
                }
            }
    }
}