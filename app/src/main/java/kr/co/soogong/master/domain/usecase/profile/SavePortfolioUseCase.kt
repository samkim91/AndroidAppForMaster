package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.utility.MultipartGenerator
import javax.inject.Inject

@Reusable
class SavePortfolioUseCase @Inject constructor(
    private val profileService: ProfileService,
) {
    operator fun invoke(portfolio: PortfolioDto): Single<PortfolioDto> {
        val portfolioDto = MultipartGenerator.createJson(portfolio)
        val attachments = MultipartGenerator.createFiles(listOf(portfolio.beforeRepairImageId!!, portfolio.afterRepairImageId!!))

        return profileService.savePortfolio(portfolioDto, attachments)
    }
}