package kr.co.soogong.master.ui.requirements.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.usecase.GetEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getEstimationListUseCase: GetEstimationListUseCase
) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _progressList = MutableLiveData<List<RequirementCard>>(emptyList())
    val progressList: LiveData<List<RequirementCard>>
        get() = _progressList

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
            _progressList.value = list
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
    }
}