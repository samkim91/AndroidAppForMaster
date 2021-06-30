package kr.co.soogong.master.domain.usecase.mypage

import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import kr.co.soogong.master.data.dao.mypage.NoticeDao
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val masterDao: MasterDao,
    private val requirementDao: RequirementDao,
    private val noticeDao: NoticeDao,
    private val sharedPreferences: SharedPreferences,
) {
    operator fun invoke() {
        Firebase.auth.signOut()

        requirementDao.removeAll()
        masterDao.removeAll()
        noticeDao.removeAll()

        sharedPreferences.edit().clear().apply()
    }
}