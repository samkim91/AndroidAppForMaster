package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.profile.RequiredInformation
import javax.inject.Inject

@Reusable
class GetBusinessTypesUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): List<BusinessType> {
        if (BuildConfig.DEBUG) {
            return RequiredInformation.TEST_REQUIRED_INFORMATION.businessTypes
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.requiredInformation?.businessTypes
                ?: RequiredInformation.NULL_REQUIRED_INFORMATION.businessTypes
        }
    }
}