package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.mypage.NoticeDao
import kr.co.soogong.master.data.dto.mypage.NoticeDto
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.network.mypage.MyPageService
import javax.inject.Inject

@Reusable
class GetNoticeUseCase @Inject constructor(
    private val myPageService: MyPageService,
    private val noticeDao: NoticeDao,
) {
    operator fun invoke(id: Int): Single<Notice> {
        return noticeDao.getById(id)
            .switchIfEmpty(
                myPageService.getNotice(id).map {
                    Notice.fromNoticeDto(it)
                }.doOnSuccess {
                    noticeDao.insert(it)
                }
            ).doOnSuccess {
                noticeDao.updateRead(it.id, false)
            }
    }
}