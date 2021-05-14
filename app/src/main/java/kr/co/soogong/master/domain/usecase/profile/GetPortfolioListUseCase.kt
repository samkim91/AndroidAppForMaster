package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.Portfolio
import javax.inject.Inject

@Reusable
class GetPortfolioListUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): List<Portfolio> {
        if(BuildConfig.DEBUG){
            return listOf(Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO)
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.basicInformation?.portfolios ?: emptyList()
        }
    }
}