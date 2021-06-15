package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.OtherFlexibleOptions
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class SaveOtherFlexibleOptionsUseCase @Inject constructor(
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(otherFlexibleOptions: OtherFlexibleOptions): Single<Response> {
        return Single.just(Response.NULL_RESPONSE)
        // TODO: 2021/06/15

//        return profileService.saveOtherFlexibleOptions(
//            getMasterUidFromSharedUseCase()!!,
//            otherFlexibleOptions
//        )
    }
}