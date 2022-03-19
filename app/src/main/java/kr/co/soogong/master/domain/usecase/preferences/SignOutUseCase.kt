package kr.co.soogong.master.domain.usecase.preferences

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.data.source.local.preferences.NoticeDao
import kr.co.soogong.master.data.source.local.profile.MasterDao
import kr.co.soogong.master.data.source.local.requirement.RequirementDao
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val masterDao: MasterDao,
    private val requirementDao: RequirementDao,
    private val noticeDao: NoticeDao,
    private val sharedPreferences: SharedPreferences,
) {
    // TODO: 2022/02/16 repository 들을 생성자로 가져와서, 삭제하는 것으로 변경 필요 !!

    operator fun invoke() {
        // sign out 을 하면 firebase auth 의 해당 계정 식별자가 reset 되는 문제가 있어서,
        // 당분간 shared preferences 로 로그인 여부를 확인하는 것으로 유지
//        Firebase.auth.signOut()

        requirementDao.removeAll()
        masterDao.removeAll()
        noticeDao.removeAll()

        sharedPreferences.edit().clear().apply()
    }
}