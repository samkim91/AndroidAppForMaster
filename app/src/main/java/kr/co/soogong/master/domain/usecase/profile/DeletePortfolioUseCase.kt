package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class DeletePortfolioUseCase @Inject constructor(
    private val masterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(itemId: Int): Single<Response> {
        return Single.just(Response.NULL_RESPONSE)
        // TODO: 2021/06/15  
//        return profileService.deletePortfolio(masterUidFromSharedUseCase()!!, itemId)
    }
}