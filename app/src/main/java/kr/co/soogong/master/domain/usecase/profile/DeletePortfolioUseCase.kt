package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.datasource.network.profile.ProfileService
import okhttp3.ResponseBody
import javax.inject.Inject

@Reusable
class DeletePortfolioUseCase @Inject constructor(
    private val profileService: ProfileService,
) {
    // TODO: 2022/02/16 repository 를 생성자로 가져와서, 작업하도록 변경 필요 !!
    operator fun invoke(portfolioId: Int): Single<ResponseBody> {
        return profileService.deletePortfolio(portfolioId)
    }
}
