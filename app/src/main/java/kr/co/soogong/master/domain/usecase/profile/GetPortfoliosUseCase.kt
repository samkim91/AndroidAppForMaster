package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.profile.PortfolioDto
import kr.co.soogong.master.data.repository.ProfileRepository
import javax.inject.Inject

@Reusable
class GetPortfoliosUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(
        type: String,
        offset: Int,
        pageSize: Int,
    ): Single<PageableContentDto<PortfolioDto>> {
        return profileRepository.getPortfolios(type.lowercase(), offset, pageSize, 1, "id")
    }
}
