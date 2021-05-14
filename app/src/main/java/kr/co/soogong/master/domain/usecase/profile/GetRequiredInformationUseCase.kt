package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.MyReview
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.data.profile.RequiredInformation
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetRequiredInformationUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): RequiredInformation {
        if(BuildConfig.DEBUG){
            return RequiredInformation.TEST_REQUIRED_INFORMATION
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.requiredInformation ?: RequiredInformation.NULL_REQUIRED_INFORMATION
        }
    }
}