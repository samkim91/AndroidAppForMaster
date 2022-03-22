package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.source.local.preferences.NoticeDao
import kr.co.soogong.master.data.source.network.preferences.PreferencesService
import kr.co.soogong.master.data.entity.auth.VersionDto
import kr.co.soogong.master.data.entity.preferences.NoticeDto
import timber.log.Timber
import javax.inject.Inject

@Reusable
class PreferencesRepository @Inject constructor(
    private val preferencesService: PreferencesService,
    private val noticeDao: NoticeDao,
) {

    // 공지사항을 불러오는 로직은 다음과 같음.
    // 1. 서버에서 데이터를 받아오고, 로컬에 저장.
    // 2. 로컬에서만 데이터를 가져옴. 사유 : 읽은 공지사항인지의 값을 로컬에서만 가지고 있기 때문.
    fun getNotices(sections: List<String>): Single<List<NoticeDto>> =
        preferencesService.getNotices(sections)
            .doOnSuccess { notices ->
                Timber.tag(TAG).w("getNotices doOnSuccess: $notices")
                noticeDao.insertNotice(*notices.toTypedArray())
            }
            .flatMap {
                Timber.tag(TAG).w("getNotices flatMap: $it")
                noticeDao.getNotices()
            }

    fun updateNoticeIsNew(id: Int) {
        noticeDao.updateNoticeIsNew(id)
    }

    suspend fun setPushAtNight(masterUid: String) {
        preferencesService.setPushAtNight(masterUid)
    }

    suspend fun setMarketingPush(masterUid: String) {
        preferencesService.setMarketingPush(masterUid)
    }

    fun getVersion(): Single<VersionDto> =
        preferencesService.getVersion()

    companion object {
        private const val TAG = "PreferencesRepository"
    }
}