package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.FlexibleCost
import javax.inject.Inject

@Reusable
class GetFlexibleCostUseCase @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
) {
    operator fun invoke(): Single<FlexibleCost> {
        return getProfileUseCase().map {
            it.basicInformation?.flexibleCost
        }
    }
}