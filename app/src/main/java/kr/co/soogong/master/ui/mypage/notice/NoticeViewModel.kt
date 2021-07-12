package kr.co.soogong.master.ui.mypage.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.mypage.Notice
import kr.co.soogong.master.domain.usecase.mypage.GetNoticeListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getNoticeListUseCase: GetNoticeListUseCase
) : BaseViewModel() {
    private val _list: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())
    val list: LiveData<List<Notice>>
        get() = _list

    fun getNoticeList() {
        getNoticeListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                _list.postValue(list.sortedByDescending { it.id })
            }, {
                Timber.tag(TAG).w("getNoticeList: $it")
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "NoticeViewModel"
    }
}