package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.PriceByProject
import javax.inject.Inject

@Reusable
class GetPriceByProjectUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(priceByProjectId: Int): Single<PriceByProject> {
//        if(BuildConfig.DEBUG){
//            return PriceByProject.TEST_PRICE_BY_PROJECT
//        }

        return getProfileFromLocalUseCase().map { profile ->
             profile.basicInformation?.priceByProjects?.find { priceByProject ->
                priceByProject.id == priceByProjectId
            }
        }
    }
}