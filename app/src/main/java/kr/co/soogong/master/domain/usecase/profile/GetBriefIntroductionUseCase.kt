package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.RequiredInformation
import javax.inject.Inject

@Reusable
class GetBriefIntroductionUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): String {
        if (BuildConfig.DEBUG) {
            return RequiredInformation.TEST_REQUIRED_INFORMATION.briefIntroduction
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.requiredInformation?.briefIntroduction
                ?: RequiredInformation.NULL_REQUIRED_INFORMATION.briefIntroduction
        }
    }
}