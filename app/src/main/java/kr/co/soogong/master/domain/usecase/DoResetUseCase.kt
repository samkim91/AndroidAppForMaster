package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import kr.co.soogong.master.domain.estimation.EstimationDao
import javax.inject.Inject

@Reusable
class DoResetUseCase @Inject constructor(
    private val estimationDao: EstimationDao,
    private val userDao: EstimationDao,
    private val setMasterKeyCodeUseCase: SetMasterKeyCodeUseCase
) {
    suspend operator fun invoke() {
        estimationDao.removeAll()
        userDao.removeAll()
        setMasterKeyCodeUseCase("", false)

    }
}