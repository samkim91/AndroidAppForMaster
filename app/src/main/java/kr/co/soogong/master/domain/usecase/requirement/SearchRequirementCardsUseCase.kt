package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.common.PageableContentDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.repository.RequirementRepository
import java.net.HttpURLConnection
import javax.inject.Inject

@Reusable
class SearchRequirementCardsUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
) {
    operator fun invoke(
        searchingText: String,
        searchingPeriod: Int,
        readYns: Boolean? = null,
        offset: Int,
        pageSize: Int,
    ): Single<PageableContentDto<RequirementCard>> {
        return requirementRepository.searchRequirementsFromServer(searchingText,
            searchingPeriod,
            readYns,
            offset,
            pageSize,
            1       // order 1 생성시간역순, 0 생성시간순
        ).map { responseDto ->
            if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK) {
                responseDto.data?.let { pageableContentDto ->
                    PageableContentDto(
                        content = pageableContentDto.content.map { requirementCardDto ->
                            RequirementCard.fromRequirementCardDto(requirementCardDto)
                        },
                        pageable = pageableContentDto.pageable,
                        last = pageableContentDto.last,
                        numberOfElements = pageableContentDto.numberOfElements,
                    )
                }
            } else {
                throw Exception(responseDto.messageKo)
            }
        }
    }
}