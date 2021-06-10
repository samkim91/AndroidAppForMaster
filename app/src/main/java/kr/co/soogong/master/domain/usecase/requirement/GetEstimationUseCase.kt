package kr.co.soogong.master.domain.usecase.requirement

import androidx.lifecycle.LiveData
import dagger.Reusable
import kr.co.soogong.master.data.model.requirement.Estimation
import kr.co.soogong.master.data.dao.estimation.EstimationDao
import javax.inject.Inject

@Reusable
class GetEstimationUseCase @Inject constructor(
    private val estimationDao: EstimationDao
) {
    operator fun invoke(estimationId: String): LiveData<Estimation?> {
        return estimationDao.getItem(estimationId)
    }
}