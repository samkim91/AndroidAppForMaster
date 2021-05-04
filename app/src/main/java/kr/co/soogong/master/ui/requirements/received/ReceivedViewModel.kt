package kr.co.soogong.master.ui.requirements.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.usecase.GetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.GetReceivedEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    private val getMasterApprovalUseCase: GetMasterApprovalUseCase,
    private val getReceivedEstimationListUseCase: GetReceivedEstimationListUseCase
) : BaseViewModel() {
    private val _isApprovedMaster = MutableLiveData<Boolean>(getMasterApprovalUseCase())
    val isApprovedMaster: LiveData<Boolean>
        get() = _isApprovedMaster

    private val _requirementList = MutableLiveData<List<RequirementCard>>(emptyList())
    val requirementList: LiveData<List<RequirementCard>>
        get() = _requirementList

    fun requestList() {
        viewModelScope.launch {
            val list = getReceivedEstimationListUseCase()
            updatedBadge(list.size)
            _requirementList.value = list
        }
    }

    fun onFilterChange(index: Int) {
        viewModelScope.launch {
            val list = when (index) {
                0 -> {
                    getReceivedEstimationListUseCase()
                }
                1 -> {
                    getReceivedEstimationListUseCase().filter { it.status == EstimationStatus.Request }
                }
                2 -> {
                    getReceivedEstimationListUseCase().filter { it.status == EstimationStatus.Waiting }
                }
                else -> {
                    emptyList()
                }
            }
            _requirementList.value = list
            setAction(UPDATE_LIST)
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
        const val UPDATE_LIST = "UPDATE_LIST"
    }
}