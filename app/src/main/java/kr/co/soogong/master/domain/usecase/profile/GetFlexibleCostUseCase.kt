package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.FlexibleCost
import javax.inject.Inject

@Reusable
class GetFlexibleCostUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<FlexibleCost> {
//        if(BuildConfig.DEBUG) {
//            return FlexibleCost.TEST_FLEXIBLE_COST
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.basicInformation?.flexibleCost
        }
    }
}