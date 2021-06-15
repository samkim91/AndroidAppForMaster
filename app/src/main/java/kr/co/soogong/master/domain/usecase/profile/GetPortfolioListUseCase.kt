package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.Portfolio
import javax.inject.Inject

@Reusable
class GetPortfolioListUseCase @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
) {
    operator fun invoke(): Single<List<Portfolio>> {
        return getProfileUseCase().map { profile ->
            profile.basicInformation?.portfolios
        }
    }
}