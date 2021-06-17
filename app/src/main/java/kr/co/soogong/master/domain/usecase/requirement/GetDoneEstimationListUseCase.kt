package kr.co.soogong.master.domain.usecase.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.model.requirement.RequirementCard
import javax.inject.Inject

class GetDoneEstimationListUseCase @Inject constructor(
//    private val requirementDao: RequirementDao,
    private val getRequirementListUseCase: GetRequirementListUseCase,
) {
    operator fun invoke(): Single<List<RequirementCard>> {
        // TODO: 2021/06/17 Repository pattern을 사용하도록 변경 필요..
        return getRequirementListUseCase("Done").map { list ->
            list.map {
                RequirementCard.fromRequirementDto(it)
            }
        }
    }
}