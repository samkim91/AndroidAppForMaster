package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.profile.PriceByProject
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SavePriceByProjectUseCase @Inject constructor(
    private val getMasterIdUseCase: GetMasterIdUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(priceByProject: PriceByProject): Single<Response> {
        return profileService.savePriceByProject(getMasterIdUseCase()!!, priceByProject)
    }
}