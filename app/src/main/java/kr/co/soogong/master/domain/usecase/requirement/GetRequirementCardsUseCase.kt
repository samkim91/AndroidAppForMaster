package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.domain.entity.requirement.RequirementCard
import kr.co.soogong.master.data.repository.RequirementRepository
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@Reusable
class GetRequirementCardsUseCase @Inject constructor(
    private val requirementRepository: RequirementRepository,
    private val masterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    operator fun invoke(
        status: List<String>? = null,
        searchingText: String? = null,
        searchingPeriod: Int? = null,
        readYn: Boolean? = null,
        offset: Int,
        pageSize: Int,
    ): Single<PageableContentDto<RequirementCard>> {
        return requirementRepository.getRequirementsByStatusAndSearch(
            masterUidFromSharedUseCase(),
            status,
            searchingText,
            searchingPeriod,
            readYn,
            offset,
            pageSize,
            1)      // order 1 생성시간역순, 0 생성시간순
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