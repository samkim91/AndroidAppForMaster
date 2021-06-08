package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SaveEmailUseCase @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(email: String): Single<Response> {
        if (BuildConfig.DEBUG) {

        }

        return Single.just(Response.TEST_RESPONSE)

        //Todo.. 추가 작업 필요
//        return profileService.saveFlexibleCost(getMasterKeyCodeUseCase()!!, briefIntroduction)
    }
}