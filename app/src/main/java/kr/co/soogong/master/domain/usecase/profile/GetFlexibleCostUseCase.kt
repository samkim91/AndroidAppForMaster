package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.FlexibleCost
import javax.inject.Inject

@Reusable
class GetFlexibleCostUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): FlexibleCost {
        if(BuildConfig.DEBUG) {
            return FlexibleCost.TEST_FLEXIBLE_COST
        }

        getProfileFromLocalUseCase().let{ profile ->
            return profile.basicInformation?.flexibleCost ?: FlexibleCost.NULL_FLEXIBLE_COST
        }
    }
}