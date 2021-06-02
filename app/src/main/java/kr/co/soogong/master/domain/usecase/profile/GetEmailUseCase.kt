package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.BasicInformation
import kr.co.soogong.master.data.profile.Profile
import kr.co.soogong.master.data.profile.RequiredInformation
import javax.inject.Inject

@Reusable
class GetEmailUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): String? {
        if (BuildConfig.DEBUG) {
            return Profile.TEST_PROFILE.basicInformation?.email
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.basicInformation?.email
                ?: BasicInformation.NULL_BASIC_INFORMATION.email
        }
    }
}