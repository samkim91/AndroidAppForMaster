package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.PriceByProject
import javax.inject.Inject

@Reusable
class GetPriceByProjectListUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): List<PriceByProject> {
        if(BuildConfig.DEBUG){
            return listOf(PriceByProject.TEST_PRICE_BY_PROJECT, PriceByProject.TEST_PRICE_BY_PROJECT)
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.basicInformation?.priceByProjects ?: emptyList()
        }
    }
}