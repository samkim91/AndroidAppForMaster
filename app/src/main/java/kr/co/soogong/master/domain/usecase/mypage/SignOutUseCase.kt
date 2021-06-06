package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.usecase.auth.SetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.auth.SetMasterKeyCodeUseCase
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val estimationDao: EstimationDao,
    private val userDao: EstimationDao,
    private val setMasterKeyCodeUseCase: SetMasterKeyCodeUseCase,
    private val setMasterApprovalUseCase: SetMasterApprovalUseCase

) {
    suspend operator fun invoke() {
        estimationDao.removeAll()
        userDao.removeAll()
        setMasterKeyCodeUseCase("")
        setMasterApprovalUseCase(false)

    }
}