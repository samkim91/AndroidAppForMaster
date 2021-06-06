package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.usecase.SetAccessTokenUseCase
import kr.co.soogong.master.domain.usecase.SetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.SetMasterKeyCodeUseCase
import kr.co.soogong.master.domain.usecase.SetRefreshTokenUseCase
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val estimationDao: EstimationDao,
    private val userDao: EstimationDao,
    private val setMasterKeyCodeUseCase: SetMasterKeyCodeUseCase,
    private val setMasterApprovalUseCase: SetMasterApprovalUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
) {
    suspend operator fun invoke() {
        estimationDao.removeAll()
        userDao.removeAll()
        setMasterKeyCodeUseCase("")
        setMasterApprovalUseCase(false)
        setAccessTokenUseCase("")
        setRefreshTokenUseCase("")
    }
}