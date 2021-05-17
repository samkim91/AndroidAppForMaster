package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.BusinessUnitInformation
import kr.co.soogong.master.data.profile.RequiredInformation
import javax.inject.Inject

@Reusable
class GetBusinessUnitInformationUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): BusinessUnitInformation {
        if(BuildConfig.DEBUG){
            return RequiredInformation.TEST_REQUIRED_INFORMATION.businessUnitInformation
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.requiredInformation?.businessUnitInformation ?: RequiredInformation.NULL_REQUIRED_INFORMATION.businessUnitInformation
        }
    }
}