package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.dao.estimation.EstimationDao
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import javax.inject.Inject

@Reusable
class GetEstimationListUseCase @Inject constructor(
    private val estimationDao: EstimationDao,
    private val requirementService: RequirementService,
    private val getMasterIdFromSharedUseCase: GetMasterIdFromSharedUseCase
) {
    suspend operator fun invoke(): List<RequirementCard> {
        if(BuildConfig.DEBUG) return emptyList()

        val list = requirementService.getEstimationList(getMasterIdFromSharedUseCase())

        estimationDao.insert(list)

        return list.map {
            RequirementCard.from(it)
        }

    }
}