package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.global.CodeTable
import javax.inject.Inject

@Reusable
class GetPortfolioUseCase @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
) {
    operator fun invoke(portfolioId: Int, type: String): Single<PortfolioDto> {
        return getProfileUseCase().map { profile ->
            when (type) {
                CodeTable.PORTFOLIO.code -> profile.basicInformation?.portfolios
                else -> profile.basicInformation?.priceByProjects
            }
        }.map {
            it.find { portfolioDto -> portfolioDto.id == portfolioId }
        }
    }
}