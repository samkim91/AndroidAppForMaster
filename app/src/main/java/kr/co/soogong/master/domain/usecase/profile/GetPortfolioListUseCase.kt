package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import javax.inject.Inject

@Reusable
class GetPortfolioListUseCase @Inject constructor(
    private val getMasterFromLocalUseCase: GetMasterFromLocalUseCase,
) {
    operator fun invoke(type: String): Single<List<PortfolioDto>> {
        return getMasterFromLocalUseCase().map { profile ->
            when(type) {
                "portfolio" -> profile.basicInformation?.portfolios
                else -> profile.basicInformation?.priceByProjects
            }
        }
    }
}