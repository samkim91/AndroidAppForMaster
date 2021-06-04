package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.network.MyPageService
import javax.inject.Inject

@Reusable
class GetNoticeListUseCase @Inject constructor(
    private val myPageService: MyPageService
) {
    operator fun invoke(): Single<List<Notice>> {
        return myPageService.getNoticeList()
    }
}