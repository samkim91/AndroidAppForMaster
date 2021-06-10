package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.RequiredInformation
import javax.inject.Inject

@Reusable
class GetRequiredInformationUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<RequiredInformation> {
//        if(BuildConfig.DEBUG){
//            return RequiredInformation.TEST_REQUIRED_INFORMATION
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.requiredInformation
        }
    }
}