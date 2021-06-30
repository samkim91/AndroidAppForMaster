package kr.co.soogong.master.ui.mypage.notice.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.domain.usecase.mypage.GetNoticeUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.mypage.notice.detail.NoticeDetailActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
    private val getNoticeUseCase: GetNoticeUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val noticeId = NoticeDetailActivityHelper.getNoticeIdFromSavedState(savedStateHandle)

    private val _notice = MutableLiveData<Notice>()
    val notice: LiveData<Notice>
        get() = _notice

    fun requestNotice() {
        getNoticeUseCase(noticeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestNotice successfully: $it")
                    _notice.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("requestNotice failed: $it")
                    setAction(REQUEST_FAILED)
                })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "NoticeViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}