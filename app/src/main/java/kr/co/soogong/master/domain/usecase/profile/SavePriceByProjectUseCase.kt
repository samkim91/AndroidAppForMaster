package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.profile.PriceByProject
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SavePriceByProjectUseCase @Inject constructor(
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(priceByProject: PriceByProject): Single<Response> {
        return profileService.savePriceByProject(getMasterKeyCodeUseCase()!!, priceByProject)
    }
}