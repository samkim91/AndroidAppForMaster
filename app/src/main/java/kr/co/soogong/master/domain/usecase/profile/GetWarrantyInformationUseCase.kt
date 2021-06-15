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

        // TODO: 2021/06/15 수정작업
        return Single.just(WarrantyInformation.NULL_WARRANTY_INFORMATION)

//        return getProfileFromLocalUseCase().map { profile ->
//             profile.requiredInformation?.warrantyInformation
//        }
    }
}