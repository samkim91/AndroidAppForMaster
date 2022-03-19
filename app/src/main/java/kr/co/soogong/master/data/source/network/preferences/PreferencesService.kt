package kr.co.soogong.master.data.source.network.preferences

import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.VersionDto
import kr.co.soogong.master.data.entity.preferences.NoticeDto
import retrofit2.Retrofit
import javax.inject.Inject

class PreferencesService @Inject constructor(
    retrofit: Retrofit,
) {
    private val preferencesInterface = retrofit.create(PreferencesInterface::class.java)

    fun getNotices(sections: List<String>): Single<List<NoticeDto>> {
        return preferencesInterface.getNotices(sections)
    }

    fun getNotice(id: Int): Single<NoticeDto> {
        return preferencesInterface.getNotice(id)
    }

    suspend fun setPushAtNight(masterUid: String) {
        preferencesInterface.setPushAtNight(masterUid)
    }

    suspend fun setMarketingPush(masterUid: String) {
        preferencesInterface.setMarketingPush(masterUid)
    }

    fun getVersion(): Single<VersionDto> =
        preferencesInterface.getVersion()
}