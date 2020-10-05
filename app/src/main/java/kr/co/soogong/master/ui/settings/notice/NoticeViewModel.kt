package kr.co.soogong.master.ui.settings.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.notice.Notice
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class NoticeViewModel : BaseViewModel() {
    private val _list: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val list: LiveData<List<Notice>>
        get() = _list

    fun getNoticeList() {
        HttpClient.getNoticeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _list.postValue(it)
            }, {
                Timber.tag(TAG).w("getNoticeList: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "NoticeViewModel"
    }
}