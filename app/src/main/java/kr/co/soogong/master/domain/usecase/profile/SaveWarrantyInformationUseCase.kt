package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.WarrantyInformation
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class SaveWarrantyInformationUseCase @Inject constructor(
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(warrantyInformation: WarrantyInformation): Single<Response> {
        if (BuildConfig.DEBUG) {

        }

        return Single.just(Response.TEST_RESPONSE)

        //Todo.. 추가 작업 필요
//        return profileService.saveFlexibleCost(getMasterKeyCodeUseCase()!!, briefIntroduction)
    }
}