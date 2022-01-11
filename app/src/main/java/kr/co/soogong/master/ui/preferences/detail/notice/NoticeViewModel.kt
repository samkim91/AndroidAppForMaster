package kr.co.soogong.master.ui.preferences.detail.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.domain.usecase.mypage.GetNoticesUseCase
import kr.co.soogong.master.domain.usecase.mypage.UpdateNoticeIsNewUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getNoticesUseCase: GetNoticesUseCase,
    private val updateNoticeIsNewUseCase: UpdateNoticeIsNewUseCase,
) : BaseViewModel() {
    private val _list: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val list: LiveData<List<Notice>>
        get() = _list

    init {
        requestNotice()
    }

    private fun requestNotice() {
        Timber.tag(TAG).d("requestNotice: ")
        getNoticesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    Timber.tag(TAG).w("requestNotice onSuccess: $list")
                    _list.value = list
                },
                onError = {
                    Timber.tag(TAG).w("requestNotice failed: $it")
                    setAction(REQUEST_FAILED)
                }
            )
            .addToDisposable()
    }

    fun updateNoticeIsNew(id: Int) {
        updateNoticeIsNewUseCase(id)
    }

    companion object {
        private const val TAG = "NoticeViewModel"

        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}