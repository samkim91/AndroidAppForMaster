package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.major.BusinessType
import javax.inject.Inject

@Reusable
class GetBusinessTypesUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<List<BusinessType>> {
//        if (BuildConfig.DEBUG) {
//            return RequiredInformation.TEST_REQUIRED_INFORMATION.businessTypes
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.requiredInformation?.businessTypes
        }
    }
}