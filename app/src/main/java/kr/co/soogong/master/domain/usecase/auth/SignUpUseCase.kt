package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.network.auth.AuthService
import javax.inject.Inject

@Reusable
class SignUpUseCase @Inject constructor(
    private val authService: AuthService,
    private val saveMasterIdInSharedUseCase: SaveMasterIdInSharedUseCase,
    private val saveMasterUidInSharedUseCase: SaveMasterUidInSharedUseCase,
    private val saveMasterApprovalUseCase: SaveMasterApprovalUseCase,
    private val masterDao: MasterDao,
) {
    operator fun invoke(masterDto: MasterDto): Single<MasterDto> {
        return authService.signUp(masterDto).doOnSuccess {
            saveMasterIdInSharedUseCase(it.id!!)
            saveMasterUidInSharedUseCase(it.uid!!)
            if (it.subscriptionPlan != "NotApproved" || it.subscriptionPlan != "RequestApprove") saveMasterApprovalUseCase(true)

            masterDao.insert(it)
        }.doOnError {
            Firebase.auth.signOut()
        }
    }
}