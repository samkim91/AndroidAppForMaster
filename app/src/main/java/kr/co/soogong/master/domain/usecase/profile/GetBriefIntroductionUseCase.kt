package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class GetBriefIntroductionUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<String> {
//        if (BuildConfig.DEBUG) {
//            return RequiredInformation.TEST_REQUIRED_INFORMATION.briefIntroduction
//        }
// TODO: 2021/06/15 수정작업
        return Single.just("")
//        return getProfileFromLocalUseCase().map { profile ->
//
//            profile.requiredInformation?.briefIntroduction
//        }
    }
}