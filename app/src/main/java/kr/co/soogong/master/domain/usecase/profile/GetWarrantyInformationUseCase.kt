package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.RequiredInformation
import kr.co.soogong.master.data.profile.WarrantyInformation
import javax.inject.Inject

@Reusable
class GetWarrantyInformationUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): WarrantyInformation {
        if (BuildConfig.DEBUG) {
            return RequiredInformation.TEST_REQUIRED_INFORMATION.warrantyInformation
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.requiredInformation?.warrantyInformation
                ?: RequiredInformation.NULL_REQUIRED_INFORMATION.warrantyInformation
        }
    }
}