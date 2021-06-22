package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.network.auth.AuthService
import kr.co.soogong.master.utility.extension.getNullable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val authService: AuthService,
    private val saveMasterIdInSharedUseCase: SaveMasterIdInSharedUseCase,
    private val saveMasterUidInSharedUseCase: SaveMasterUidInSharedUseCase,
    private val saveMasterApprovalUseCase: SaveMasterApprovalUseCase,
    private val masterDao: MasterDao,
) {
    operator fun invoke(uid: String): Single<MasterDto> {
        return authService.signIn(uid).doOnSuccess {
            saveMasterIdInSharedUseCase(it.masterId!!)
            saveMasterUidInSharedUseCase(it.uid!!)
            if (it.subscriptionPlan != "NotApproved") saveMasterApprovalUseCase(true)

            masterDao.insert(it)
        }.doOnError {
            Firebase.auth.signOut()
        }
    }
}