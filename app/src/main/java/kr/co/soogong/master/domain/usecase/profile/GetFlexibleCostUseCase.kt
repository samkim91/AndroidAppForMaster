package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.FlexibleCost
import javax.inject.Inject

@Reusable
class GetFlexibleCostUseCase @Inject constructor(
    private val getMasterFromLocalUseCase: GetMasterFromLocalUseCase,
) {
    operator fun invoke(): Single<FlexibleCost> {
        return getMasterFromLocalUseCase().map {
            it.basicInformation?.flexibleCost
        }
    }
}