package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Flowable
import kr.co.soogong.master.data.dto.requirement.search.SearchDto
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
    ): Flowable<List<RequirementCard>> {
        return requirementRepository.searchRequirements(searchingText, searchingPeriod)
            .map { dtoList ->
                dtoList.map { dto ->
                    RequirementCard.fromRequirementDto(dto)
                }
            }
    }
}