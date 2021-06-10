package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.Portfolio
import javax.inject.Inject

@Reusable
class GetPortfolioListUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<List<Portfolio>> {
//        if(BuildConfig.DEBUG){
//            return listOf(Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO)
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.basicInformation?.portfolios
        }
    }
}