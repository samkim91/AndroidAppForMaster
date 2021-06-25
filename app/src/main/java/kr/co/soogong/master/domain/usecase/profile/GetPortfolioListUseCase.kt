package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.ui.profile.PortfolioCodeTable
import javax.inject.Inject

@Reusable
class GetPortfolioListUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(type: String): Single<List<PortfolioDto>> {
        return getProfileFromLocalUseCase().map { profile ->
            when(type) {
                PortfolioCodeTable.code -> profile.basicInformation?.portfolios
                else -> profile.basicInformation?.priceByProjects
            }
        }
    }
}