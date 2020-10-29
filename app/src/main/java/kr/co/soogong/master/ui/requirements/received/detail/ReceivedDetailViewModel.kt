package kr.co.soogong.master.ui.requirements.received.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber
import java.util.*

class ReceivedDetailViewModel(
    private val repository: Repository,
    private val keycode: String
) : BaseViewModel() {

    private val _requirement = repository.getRequirement(keycode)
    val requirement: LiveData<Requirement?>
        get() = _requirement

    val project: LiveData<String?>
        get() = _requirement.map {
            it?.project
        }

    val location: LiveData<String>
        get() = _requirement.map {
            it?.location ?: ""
        }

    val date: LiveData<Date?>
        get() = _requirement.map {
            it?.date
        }

    val content: LiveData<String?>
        get() = _requirement.map {
            it?.content
        }

    val type: LiveData<String?>
        get() = _requirement.map {
            it?.houseType
        }

    val image: LiveData<String?>
        get() = _requirement.map {
            it?.image
        }

    val status: LiveData<String?>
        get() = _requirement.map {
            it?.status
        }

    val questions: LiveData<String?>
        get() = _requirement.map {
            it?.questions
        }

    val wantedDate: String = ""

    val isBag: String = ""

    val contact: String = ""

    private val _event = MutableLiveData<Event<String>>()
    val event: LiveData<Event<String>>
        get() = _event

    fun onClickedDenied() {
        HttpClient.refuseRequirement(branchKeycode = InjectHelper.keyCode, keycode = keycode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModelScope.launch {
                    repository.removeRequirement(keycode)
                    _event.value = Event(DENIED_EVENT)
                }
            }, {
                Timber.tag(TAG).e("onClickedDenied: $it")
            })
            .addToDisposable()
    }

    fun onClickedAccept() {
        _event.value = Event(ACCEPT_EVENT)
    }

    companion object {
        private const val TAG = "DetailViewModel"
        const val DENIED_EVENT = "DENIED_EVENT"
        const val ACCEPT_EVENT = "ACCEPT_EVENT"
    }
}