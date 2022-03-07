package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.repository.ProfileRepository
import okhttp3.ResponseBody
import javax.inject.Inject

@Reusable
class DeletePortfolioUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    operator fun invoke(portfolioId: Int): Single<ResponseBody> {
        return profileRepository.deletePortfolio(portfolioId)
    }
}
