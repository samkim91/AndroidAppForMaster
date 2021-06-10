package kr.co.soogong.master.ui.requirement.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.model.requirement.EstimationStatus
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetReceivedEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
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
            sendEvent(BADGE_UPDATE, list.size)
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

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val UPDATE_LIST = "UPDATE_LIST"
    }
}