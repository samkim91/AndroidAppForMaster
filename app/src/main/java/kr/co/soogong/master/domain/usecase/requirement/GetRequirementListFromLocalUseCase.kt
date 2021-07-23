package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.model.requirement.RequirementCard
import javax.inject.Inject

@Reusable
class GetRequirementListFromLocalUseCase @Inject constructor(
    private val requirementDao: RequirementDao,
) {
    operator fun invoke(statusArray: List<String>, canceledYn: Boolean = false): Single<List<RequirementCard>> {
        return requirementDao.getListByStatus(statusArray, canceledYn)
            .map { list ->
                list.map {
                    RequirementCard.fromRequirementDto(it)
                }
            }
    }
}
