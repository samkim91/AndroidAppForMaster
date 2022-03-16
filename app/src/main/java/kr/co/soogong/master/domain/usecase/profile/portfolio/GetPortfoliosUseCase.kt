package kr.co.soogong.master.domain.usecase.profile.portfolio

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.domain.entity.profile.portfolio.IPortfolio
import java.net.HttpURLConnection
import javax.inject.Inject

@Reusable
class GetPortfoliosUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(
        type: String,
        offset: Int,
        pageSize: Int,
    ): Single<PageableContentDto<IPortfolio>> {
        return profileRepository.getPortfolios(type.lowercase(), offset, pageSize, 1, "id")
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK) {
                    responseDto.data?.let { pageableContentDto ->
                        PageableContentDto(
                            content = pageableContentDto.content.map {
                                IPortfolio.fromDto(it)
                            },
                            pageable = pageableContentDto.pageable,
                            last = pageableContentDto.last,
                            numberOfElements = pageableContentDto.numberOfElements
                        )
                    }
                } else {
                    throw Exception(responseDto.messageKo)
                }
            }
    }
}
