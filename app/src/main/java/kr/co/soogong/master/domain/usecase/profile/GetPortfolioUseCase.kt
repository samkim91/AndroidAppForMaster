package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.Portfolio
import javax.inject.Inject

@Reusable
class GetPortfolioUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
    ) {
    operator fun invoke(portfolioId: Int): Single<Portfolio> {

        return getProfileFromLocalUseCase().map { profile ->
            profile.basicInformation?.portfolios?.find { portfolio ->
                portfolio.id == portfolioId
            }
        }
    }
}