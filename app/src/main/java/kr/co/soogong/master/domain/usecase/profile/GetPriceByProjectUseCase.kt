package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.data.profile.PriceByProject
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetPriceByProjectUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(priceByProjectId: Int): PriceByProject {
        if(BuildConfig.DEBUG){
            return PriceByProject.TEST_PRICE_BY_PROJECT
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.basicInformation?.priceByProjects?.find { priceByProject ->
                priceByProject.itemId == priceByProjectId
            } ?: PriceByProject.NULL_PRICE_BY_PROJECT
        }
    }
}