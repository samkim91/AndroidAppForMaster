package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import javax.inject.Inject

@Reusable
class SavePortfolioUseCase @Inject constructor(
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(portfolio: PortfolioDto): Single<Response> {
        return profileService.savePortfolio(getMasterUidFromSharedUseCase()!!, portfolio)
    }
}