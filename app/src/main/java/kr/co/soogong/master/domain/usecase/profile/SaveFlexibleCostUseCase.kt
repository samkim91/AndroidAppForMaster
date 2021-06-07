package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.profile.FlexibleCost
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SaveFlexibleCostUseCase @Inject constructor(
    private val getMasterIdUseCase: GetMasterIdUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(flexibleCost: FlexibleCost): Single<Response> {
        return profileService.saveFlexibleCost(getMasterIdUseCase()!!, flexibleCost)
    }
}