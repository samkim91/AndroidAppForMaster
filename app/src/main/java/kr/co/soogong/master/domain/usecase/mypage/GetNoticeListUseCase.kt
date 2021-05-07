package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.network.NoticeService
import javax.inject.Inject

@Reusable
class GetNoticeListUseCase @Inject constructor(
    private val noticeService: NoticeService
) {
    operator fun invoke(): Single<List<Notice>> {
        return noticeService.getNoticeList()
    }
}