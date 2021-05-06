package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetPortfolioUseCase @Inject constructor(
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(portfolioId: Int): Portfolio {
        return profileService.getPortfolio(getMasterKeyCodeUseCase()!!, portfolioId)
    }
}