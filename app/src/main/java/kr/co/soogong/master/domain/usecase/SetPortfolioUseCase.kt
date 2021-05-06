package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SetPortfolioUseCase @Inject constructor(
    private val profileService: ProfileService,
) {
    operator fun invoke(portfolio: Portfolio): Single<Response> {
        return profileService.setPortfolio(portfolio)
    }
}