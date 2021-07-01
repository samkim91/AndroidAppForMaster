package kr.co.soogong.master.domain.usecase.mypage

import dagger.Reusable
import io.reactivex.Flowable
import kr.co.soogong.master.data.dao.mypage.NoticeDao
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.network.mypage.MyPageService
import javax.inject.Inject

@Reusable
class GetNoticeListUseCase @Inject constructor(
    private val myPageService: MyPageService,
    private val noticeDao: NoticeDao,
) {
    operator fun invoke(): Flowable<List<Notice>> {
        return myPageService.getNoticeList(typeCode = "Notice")
            .map { list ->
                list.map {
                    Notice.fromNoticeDto(it)
                }.sortedByDescending {
                    it.id
                }
            }
            .doOnSuccess {
                noticeDao.insertAll(*it.toTypedArray())
            }
            .mergeWith(
                noticeDao.getAll()
            )
    }
}