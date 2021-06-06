package kr.co.soogong.master.domain.usecase.requirement

import dagger.Reusable
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.usecase.auth.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.EstimationsService
import javax.inject.Inject

@Reusable
class GetEstimationListUseCase @Inject constructor(
    private val estimationDao: EstimationDao,
    private val estimationsService: EstimationsService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
) {
    suspend operator fun invoke(): List<RequirementCard> {
        val list = estimationsService.getEstimationList(getMasterKeyCodeUseCase())

        estimationDao.insert(list)

        return list.map {
            RequirementCard.from(it)
        }

    }
}