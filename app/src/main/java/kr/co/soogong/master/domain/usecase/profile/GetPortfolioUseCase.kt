package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.Portfolio
import javax.inject.Inject

@Reusable
class GetPortfolioUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
    ) {
    suspend operator fun invoke(portfolioId: Int): Portfolio {
        if (BuildConfig.DEBUG) {
            return Portfolio.TEST_PORTFOLIO
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.basicInformation?.portfolios?.find { portfolio ->
                portfolio.itemId == portfolioId
            } ?: Portfolio.NULL_PORTFOLIO
        }
    }
}