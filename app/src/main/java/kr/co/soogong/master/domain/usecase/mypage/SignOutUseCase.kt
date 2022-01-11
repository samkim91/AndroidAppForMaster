package kr.co.soogong.master.domain.usecase.mypage

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.data.dao.preferences.NoticeDao
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
        // sign out 을 하면 firebase auth의 해당 계정 식별자가 reset 되는 문제가 있어서,
        // 당분간 shared prefereneces 로 로그인 여부를 확인하는 것으로 유지
//        Firebase.auth.signOut()

        requirementDao.removeAll()
        masterDao.removeAll()
        noticeDao.removeAll()

        sharedPreferences.edit().clear().apply()
    }
}