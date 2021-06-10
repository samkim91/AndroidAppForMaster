package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.profile.CompanyAddress
import javax.inject.Inject

@Reusable
class GetCompanyAddressUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<CompanyAddress> {
//        if (BuildConfig.DEBUG) {
//            return RequiredInformation.TEST_REQUIRED_INFORMATION.companyAddress
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.requiredInformation?.companyAddress
        }
    }
}