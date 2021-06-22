package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.PriceByProject
import javax.inject.Inject

@Reusable
class GetPriceByProjectListUseCase @Inject constructor(
    private val getMasterFromLocalUseCase: GetMasterFromLocalUseCase,
) {
    operator fun invoke(): Single<List<PriceByProject>> {
        return getMasterFromLocalUseCase().map {
            it.basicInformation?.priceByProjects
        }
    }
}