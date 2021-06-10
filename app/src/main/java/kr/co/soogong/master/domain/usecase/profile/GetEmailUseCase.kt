package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class GetEmailUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<String> {
//        if (BuildConfig.DEBUG) {
//            return Profile.TEST_PROFILE.basicInformation?.email
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.basicInformation?.email
        }
    }
}