package kr.co.soogong.master.ui.requirements.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.usecase.GetEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    private val getEstimationListUseCase: GetEstimationListUseCase
) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _requirementList = MutableLiveData<List<RequirementCard>>(emptyList())
    val requirementList: LiveData<List<RequirementCard>>
        get() = _requirementList

    fun requestList() {
        viewModelScope.launch {
            val list = getEstimationListUseCase()

            if (list.isNullOrEmpty()) {
                _emptyList.value = true
                updatedBadge(0)
            } else {
                _emptyList.value = false
                updatedBadge(list.size)
            }

            _requirementList.value = list
        }
    }

    private val _event = MutableLiveData<Event<Pair<String, Int>>>()
    val event: LiveData<Event<Pair<String, Int>>>
        get() = _event

    private fun updatedBadge(badgeCount: Int) {
        Timber.tag(TAG).d("updatedBadge: $badgeCount")
        _event.value = Event(BADGE_UPDATE to badgeCount)
    }

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}