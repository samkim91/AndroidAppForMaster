package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.network.profile.ProfileService
import okhttp3.ResponseBody
import javax.inject.Inject

@Reusable
class DeletePortfolioUseCase @Inject constructor(
    private val profileService: ProfileService,
) {
    operator fun invoke(portfolioId: Int): Single<ResponseBody> {
        return profileService.deletePortfolio(portfolioId)
    }
}
