package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SaveBusinessRepresentativeNameUseCase @Inject constructor(
    private val getMasterIdUseCase: GetMasterIdUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(briefIntroduction: String): Single<Response> {
        if (BuildConfig.DEBUG) {

        }

        return Single.just(Response.TEST_RESPONSE)

        //Todo.. 추가 작업 필요
//        return profileService.saveFlexibleCost(getMasterKeyCodeUseCase()!!, briefIntroduction)
    }
}