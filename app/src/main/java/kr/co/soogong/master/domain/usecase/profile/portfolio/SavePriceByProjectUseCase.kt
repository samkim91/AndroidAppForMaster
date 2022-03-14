package kr.co.soogong.master.domain.usecase.profile.portfolio

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.data.entity.profile.portfolio.SavePriceByProjectDto
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SavePriceByProjectUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(
        savePriceByProjectDto: SavePriceByProjectDto,
    ): Single<PortfolioDto> =
        profileRepository.savePriceByProject(
            savePriceByProjectJson = MultipartGenerator.createJson(savePriceByProjectDto),
        )
}
