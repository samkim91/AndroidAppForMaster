package kr.co.soogong.master.ui.requirements.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    private val repository: Repository,
    private val httpClient: HttpClient
) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _requirementList: LiveData<List<RequirementCard>> =
        repository.getEstimationList().map { list ->
            val ret = list.map { RequirementCard.from(it) }

            if (ret.isNullOrEmpty()) {
                _emptyList.value = true
                updatedBadge(0)
                return@map emptyList<RequirementCard>()
            } else {
                _emptyList.value = false
                updatedBadge(ret.size)
                return@map ret
            }
        }
    val requirementList: LiveData<List<RequirementCard>>
        get() = _requirementList

    private val _event = MutableLiveData<Event<Pair<String, Int>>>()
    val event: LiveData<Event<Pair<String, Int>>>
        get() = _event

    private fun updatedBadge(badgeCount: Int) {
        Timber.tag(TAG).d("updatedBadge: $badgeCount")
        _event.value = Event(BADGE_UPDATE to badgeCount)
    }

    fun requestList() {
        httpClient.getEstimationList(InjectHelper.keyCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                viewModelScope.launch {
                    repository.insertEstimation(it)
                }
            }
            .addToDisposable()
    }

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}