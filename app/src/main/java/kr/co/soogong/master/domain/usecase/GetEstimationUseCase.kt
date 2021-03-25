package kr.co.soogong.master.domain.usecase

import androidx.lifecycle.LiveData
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.domain.estimation.EstimationDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEstimationUseCase @Inject constructor(
    private val estimationDao: EstimationDao
) {
    operator fun invoke(estimationId: String): LiveData<Estimation?> {
        return estimationDao.getItem(estimationId)
    }
}