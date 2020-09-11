package kr.co.soogong.master.ui.requirements.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.http.HttpClient

class ReceivedViewModel(private val repository: Repository) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _requirementList: LiveData<List<ReceivedCard>> =
        repository.getRequirementList().map { list ->
            val ret = list.filter { it.status == "received" }.map { ReceivedCard.from(it) }

            if (ret.isNullOrEmpty()) {
                _emptyList.value = true
                updatedBadge(0)
                return@map emptyList<ReceivedCard>()
            } else {
                _emptyList.value = false
                updatedBadge(ret.size)
                return@map ret
            }
        }
    val requirementList: LiveData<List<ReceivedCard>>
        get() = _requirementList

    private val _event = MutableLiveData<Event<Pair<String, Int>>>()
    val event: LiveData<Event<Pair<String, Int>>>
        get() = _event

    private fun updatedBadge(badgeCount: Int) {
        _event.value = Event(BADGE_UPDATE to badgeCount)
    }

    fun requestList() {
        HttpClient.getRequirementList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                viewModelScope.launch {
                    repository.insertRequirement(it)
                }
            }
            .addToDisposable()
    }

    companion object {
        const val BADGE_UPDATE = "BADGE_UPDATE"
        private const val TAG = "ProgressViewModel"
    }
}