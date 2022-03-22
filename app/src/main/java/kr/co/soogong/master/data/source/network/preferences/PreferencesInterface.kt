package kr.co.soogong.master.data.source.network.preferences

import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.auth.VersionDto
import kr.co.soogong.master.data.entity.preferences.NoticeDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface PreferencesInterface {
    @GET(HttpContract.GET_NOTICE_LIST)
    fun getNotices(@Query("section") sections: List<String>): Single<List<NoticeDto>>

    @GET(HttpContract.GET_NOTICE)
    fun getNotice(@Query("id") id: Int): Single<NoticeDto>

    @PATCH(HttpContract.SET_PUSH_AT_NIGHT)
    suspend fun setPushAtNight(@Path("uid") masterUid: String)

    @PATCH(HttpContract.SET_MARKETING_PUSH)
    suspend fun setMarketingPush(@Path("uid") masterUid: String)

    @GET(HttpContract.GET_APP_VERSION)
    fun getVersion(): Single<VersionDto>
}