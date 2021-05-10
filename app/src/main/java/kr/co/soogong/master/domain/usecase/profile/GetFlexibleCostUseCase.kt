package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.data.profile.FlexibleCost
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetFlexibleCostUseCase @Inject constructor(
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(): FlexibleCost {
        return profileService.getFlexibleCost(getMasterKeyCodeUseCase()!!)
    }
}