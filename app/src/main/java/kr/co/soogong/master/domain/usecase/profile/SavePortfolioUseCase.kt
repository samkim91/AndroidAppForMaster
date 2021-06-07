package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SavePortfolioUseCase @Inject constructor(
    private val getMasterIdUseCase: GetMasterIdUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(portfolio: Portfolio): Single<Response> {
        return profileService.savePortfolio(getMasterIdUseCase()!!, portfolio)
    }
}