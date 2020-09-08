package kr.co.soogong.master.ui.requirements.received.estimate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber


class EstimateViewModel(
    private val repository: Repository,
    id: Long
) : BaseViewModel() {
    private lateinit var keycode: String

    private val _requirement = repository.getRequirement(id).map {
        Timber.tag(TAG).d(": $it")
        it?.let {
            keycode = it.keycode
        }
        it
    }

    val requirement: LiveData<Requirement?>
        get() = _requirement

    private val _event = MutableLiveData<Event<String>>()
    val event: LiveData<Event<String>>
        get() = _event

    fun onClickedSend(price: String, contents: String, possibleDate: String) {
        HttpClient.sendMessage(keycode, price, contents, possibleDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { message ->
                    Timber.tag(TAG).d("onClickedSend: $message")
                    _event.value = Event(SEND_EVENT)
                },
                {
                    Timber.tag(TAG).e("onClickedSend: $it")
                    _event.value = Event(SEND_EVENT)
                }
            )
            .addToDisposable()
    }

    companion object {
        private const val TAG = "DetailViewModel"
        const val SEND_EVENT = "SEND_EVENT"
    }
}