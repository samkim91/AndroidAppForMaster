package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.PriceByProject
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class SavePriceByProjectUseCase @Inject constructor(
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(priceByProject: PriceByProject): Single<Response> {
        return profileService.savePriceByProject(getMasterUidFromSharedUseCase()!!, priceByProject)
    }
}