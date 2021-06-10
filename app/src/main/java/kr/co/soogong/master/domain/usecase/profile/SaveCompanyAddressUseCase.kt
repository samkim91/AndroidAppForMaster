package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.model.profile.CompanyAddress
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.data.dto.Response
import javax.inject.Inject

@Reusable
class SaveCompanyAddressUseCase @Inject constructor(
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase,
    private val profileService: ProfileService,
) {
    operator fun invoke(companyAddress: CompanyAddress, latitude: Double, longitude: Double): Single<Response> {
        if(BuildConfig.DEBUG){

        }

        return Single.just(Response.TEST_RESPONSE)

        //Todo.. 추가 작업 필요
//        return profileService.saveFlexibleCost(getMasterKeyCodeUseCase()!!, briefIntroduction)
    }
}