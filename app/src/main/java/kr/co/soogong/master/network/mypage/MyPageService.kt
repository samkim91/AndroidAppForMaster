package kr.co.soogong.master.network.mypage

import io.reactivex.Single
import kr.co.soogong.master.data.model.mypage.Notice
import retrofit2.Retrofit
import javax.inject.Inject

class MyPageService @Inject constructor(
    retrofit: Retrofit
) {
    private val noticeInterface = retrofit.create(NoticeInterface::class.java)

    fun getNoticeList(master: String = "free"): Single<List<Notice>> {
        return noticeInterface.getNoticeList(master).map { list ->
            val ret = ArrayList<Notice>()
            for (item in list) {
                ret.add(Notice.fromJson(item))
            }
            return@map ret
        }
    }
}