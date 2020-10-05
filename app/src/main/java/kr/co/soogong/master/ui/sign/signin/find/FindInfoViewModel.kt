package kr.co.soogong.master.ui.sign.signin.find

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class FindInfoViewModel : BaseViewModel() {
    fun findInfo(name: String?, contact: String?) {
        HttpClient.findInfo(name, contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("findInfo: $it")
            }, {
                Timber.tag(TAG).w("findInfo: $it")
            })
            .addToDisposable()
    }

    private val _event = MutableLiveData<Event<Pair<String, Any?>>>()
    val event: LiveData<Event<Pair<String, Any?>>>
        get() = _event

    fun failToName() {
        _event.postValue(Event(FAIL_TO_NAME to null))
    }

    fun failToContact() {
        _event.postValue(Event(FAIL_TO_CONTACT to null))
    }

    fun successToFindInfo() {
        _event.postValue(Event(SUCCESS_TO_FIND_INFO to null))
    }

    companion object {
        private const val TAG = "FindInfoViewModel"
        const val FAIL_TO_NAME = "FAIL_TO_NAME"
        const val FAIL_TO_CONTACT = "FAIL_TO_CONTACT"
        const val SUCCESS_TO_FIND_INFO = "SUCCESS_TO_FIND_INFO"
    }
}