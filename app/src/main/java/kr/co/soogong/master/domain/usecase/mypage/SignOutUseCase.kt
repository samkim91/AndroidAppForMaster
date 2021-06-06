package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.usecase.auth.SaveAccessTokenUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveRefreshTokenUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterKeyCodeUseCase
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val estimationDao: EstimationDao,
    private val userDao: EstimationDao,
    private val saveMasterKeyCodeUseCase: SaveMasterKeyCodeUseCase,
    private val saveMasterApprovalUseCase: SaveMasterApprovalUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
) {
    suspend operator fun invoke() {
        estimationDao.removeAll()
        userDao.removeAll()
        saveMasterKeyCodeUseCase("")
        saveMasterApprovalUseCase(false)
        saveAccessTokenUseCase("")
        saveRefreshTokenUseCase("")
    }
}