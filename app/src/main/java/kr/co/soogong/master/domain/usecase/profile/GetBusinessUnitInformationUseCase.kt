package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.BusinessUnitInformation
import javax.inject.Inject

@Reusable
class GetBusinessUnitInformationUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<BusinessUnitInformation> {
//        if(BuildConfig.DEBUG){
//            return RequiredInformation.TEST_REQUIRED_INFORMATION.businessUnitInformation
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.requiredInformation?.businessUnitInformation
        }
    }
}