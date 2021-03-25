package kr.co.soogong.master.ui.mypage.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.network.NoticeService
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val noticeService: NoticeService
) : BaseViewModel() {
    private val _list: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val list: LiveData<List<Notice>>
        get() = _list

    fun getNoticeList() {
        noticeService.getNoticeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _list.postValue(it.sortedByDescending { it.date })
            }, {
                Timber.tag(TAG).w("getNoticeList: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "NoticeViewModel"
    }
}