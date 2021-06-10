package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.WarrantyInformation
import javax.inject.Inject

@Reusable
class GetWarrantyInformationUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<WarrantyInformation> {
//        if (BuildConfig.DEBUG) {
//            return Single.just(RequiredInformation.TEST_REQUIRED_INFORMATION.warrantyInformation)
//        }

        return getProfileFromLocalUseCase().map { profile ->
             profile.requiredInformation?.warrantyInformation
        }
    }
}