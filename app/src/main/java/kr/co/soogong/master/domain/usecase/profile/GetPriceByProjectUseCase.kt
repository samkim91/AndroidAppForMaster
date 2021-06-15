package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.PriceByProject
import javax.inject.Inject

@Reusable
class GetPriceByProjectUseCase @Inject constructor(
    private val getPriceByProjectListUseCase: GetPriceByProjectListUseCase,
) {
    operator fun invoke(priceByProjectId: Int): Single<PriceByProject> {
        return getPriceByProjectListUseCase().map {
            it.find { priceByProject ->
                priceByProject.id == priceByProjectId
            }
        }
    }
}