package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.PriceByProject
import javax.inject.Inject

@Reusable
class GetPriceByProjectListUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<List<PriceByProject>> {
//        if(BuildConfig.DEBUG){
//            return listOf(PriceByProject.TEST_PRICE_BY_PROJECT, PriceByProject.TEST_PRICE_BY_PROJECT)
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.basicInformation?.priceByProjects
        }
    }
}