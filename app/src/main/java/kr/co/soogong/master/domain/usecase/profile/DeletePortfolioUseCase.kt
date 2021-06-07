package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class DeletePortfolioUseCase @Inject constructor(
    private val masterIdUseCase: GetMasterIdUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(itemId: Int): Single<Response> {
        return profileService.deletePortfolio(masterIdUseCase()!!, itemId)
    }
}