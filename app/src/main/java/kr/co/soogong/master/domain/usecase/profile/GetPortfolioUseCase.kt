package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.Portfolio
import javax.inject.Inject

@Reusable
class GetPortfolioUseCase @Inject constructor(
    private val getPortfolioListUseCase: GetPortfolioListUseCase,
) {
    operator fun invoke(portfolioId: Int): Single<Portfolio> {
        return getPortfolioListUseCase().map {
            it.find { portfolio -> portfolio.id == portfolioId }
        }
    }
}