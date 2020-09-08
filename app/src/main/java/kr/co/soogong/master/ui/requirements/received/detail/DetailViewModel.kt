package kr.co.soogong.master.ui.requirements.received.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.util.Event
import timber.log.Timber
import java.util.*

class DetailViewModel(
    private val repository: Repository,
    id: Long
) : BaseViewModel() {

    private val _requirement = repository.getRequirement(id).map {
        Timber.tag(TAG).d(": $it")
        it
    }

    val requirement: LiveData<Requirement?>
        get() = _requirement

    val userName: LiveData<String?>
        get() = _requirement.map {
            it?.userName
        }

    val category: LiveData<String?>
        get() = _requirement.map {
            it?.category
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

    val houseType: LiveData<String?>
        get() = _requirement.map {
            it?.houseType
        }

    val size: LiveData<String?>
        get() = _requirement.map {
            it?.size
        }

    val image: LiveData<String?>
        get() = _requirement.map {
            it?.image
        }

    val status: LiveData<String?>
        get() = _requirement.map {
            it?.status
        }

    private val _event = MutableLiveData<Event<String>>()
    val event: LiveData<Event<String>>
        get() = _event

    fun onClickedDenied(requirementId: Long) {
        viewModelScope.launch {
            repository.removeRequirement(requirementId)
            _event.value = Event(DENIED_EVENT)
        }
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