package kr.co.soogong.master.ui.requirements.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.usecase.GetProgressEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getProgressEstimationListUseCase: GetProgressEstimationListUseCase
) : BaseViewModel() {
    private val _progressList = MutableLiveData<List<RequirementCard>>(emptyList())
    val progressList: LiveData<List<RequirementCard>>
        get() = _progressList

    fun requestList() {
        viewModelScope.launch {
            val list = getProgressEstimationListUseCase()
            updatedBadge(list.size)
            _progressList.value = list
        }
    }

    fun onFilterChange(index: Int) {
        viewModelScope.launch {
            val list = when (index) {
                0 -> {
                    getProgressEstimationListUseCase()
                }
                1 -> {
                    getProgressEstimationListUseCase().filter { it.status == EstimationStatus.Progress }
                }
                2 -> {
                    getProgressEstimationListUseCase().filter { it.status == EstimationStatus.CustomDone }
                }
                else -> {
                    emptyList()
                }
            }
            _progressList.value = list
            setAction(UPDATE_LIST)
        }
    }

    private val _event = MutableLiveData<Event<Pair<String, Int>>>()
    val event: LiveData<Event<Pair<String, Int>>>
        get() = _event

    private fun updatedBadge(badgeCount: Int) {
        _event.value = Event(BADGE_UPDATE to badgeCount)
    }

    companion object {
        private const val TAG = "ProgressViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val UPDATE_LIST = "UPDATE_LIST"
    }
}