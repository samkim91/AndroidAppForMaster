package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.major.Major
import javax.inject.Inject

@Reusable
class GetBusinessTypesUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<List<Major>> {
//        if (BuildConfig.DEBUG) {
//            return RequiredInformation.TEST_REQUIRED_INFORMATION.businessTypes
//        }

        // TODO: 2021/06/15 수정작업
        return Single.just(emptyList())

//        return getProfileFromLocalUseCase().map { profile ->
//            profile.requiredInformation?.majors
//        }
    }
}