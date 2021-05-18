package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.CompanyAddress
import kr.co.soogong.master.data.profile.RequiredInformation
import javax.inject.Inject

@Reusable
class GetCompanyAddressUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): CompanyAddress {
        if (BuildConfig.DEBUG) {
            return RequiredInformation.TEST_REQUIRED_INFORMATION.companyAddress
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.requiredInformation?.companyAddress
                ?: RequiredInformation.NULL_REQUIRED_INFORMATION.companyAddress
        }
    }
}