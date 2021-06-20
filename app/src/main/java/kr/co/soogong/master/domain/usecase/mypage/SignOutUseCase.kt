package kr.co.soogong.master.domain.usecase.mypage

import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import kr.co.soogong.master.data.dao.requirement.estimation.EstimationDao
import kr.co.soogong.master.domain.usecase.auth.SaveMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterIdInSharedUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterUidInSharedUseCase
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val estimationDao: EstimationDao,
    private val userDao: EstimationDao,
    private val sharedPreferences: SharedPreferences,
) {
    suspend operator fun invoke() {
        Firebase.auth.signOut()

        estimationDao.removeAll()
        userDao.removeAll()

        sharedPreferences.edit().clear().apply()
    }
}