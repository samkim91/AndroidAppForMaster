package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.common.PageableContentDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.repository.RequirementRepository
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@Reusable
class GetRequirementCardsUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
) {
    operator fun invoke(
        status: String,
        readYns: Boolean? = null,
        offset: Int,
        pageSize: Int,
    ): Single<PageableContentDto<RequirementCard>> {
        return requirementRepository.getRequirementsByStatus(status, readYns, offset, pageSize)
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpsURLConnection.HTTP_OK) {
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
                } else
                    throw Exception(responseDto.messageKo)
            }
    }
}