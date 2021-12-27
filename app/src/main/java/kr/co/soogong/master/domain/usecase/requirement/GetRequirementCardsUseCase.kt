package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.common.PageableContentDto
import kr.co.soogong.master.data.model.requirement.RequirementCardV2
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
    ): Single<PageableContentDto<RequirementCardV2>> {
        return requirementRepository.getRequirementsByStatus(status, readYns, offset, pageSize)
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpsURLConnection.HTTP_OK) {
                    responseDto.data?.let { data ->
                        PageableContentDto(
                            content = data.content.map { requirementDto ->
                                RequirementCardV2.fromRequirementCardDto(requirementDto)
                            },
                            pageable = data.pageable,
                            last = data.last,
                            first = data.first,
                            empty = data.empty,
                            totalElements = data.totalElements,
                            totalPages = data.totalPages,
                        )
                    }
                } else
                    throw Exception(responseDto.messageKo)
            }
    }
}