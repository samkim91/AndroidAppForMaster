package kr.co.soogong.master.domain.usecase.requirement

import io.reactivex.Flowable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.model.requirement.RequirementCard
import javax.inject.Inject

class GetDoneEstimationListUseCase @Inject constructor(
    private val requirementDao: RequirementDao,
) {
    operator fun invoke(): Single<List<RequirementCard>> {
        return requirementDao.getListByStatus(
            listOf(
                "Done",
                "Closed",
                "CanceledByClient",
                "CanceledByMaster",
                "Canceled",
                "Impossible"
            )
        ).map { list ->
            list.map {
                RequirementCard.fromRequirementDto(it)
            }
        }
    }
}