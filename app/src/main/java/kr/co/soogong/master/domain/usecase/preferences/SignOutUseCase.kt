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
        requirementDao.removeAll()
        masterDao.removeAll()
        noticeDao.removeAll()

        sharedPreferences.edit().clear().apply()
    }
}