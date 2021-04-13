package kr.co.soogong.master.ui.requirements.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.usecase.CallToCustomerUseCase
import kr.co.soogong.master.domain.usecase.GetProgressEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getProgressEstimationListUseCase: GetProgressEstimationListUseCase,
    private val callToCustomerUseCase: CallToCustomerUseCase,
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

    fun callToCustomer(estimationId: String, phoneNumber: String) {
        Timber.tag(TAG).d("callToCustomer: $estimationId / $phoneNumber")
        callToCustomerUseCase(estimationId, phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("CALL_TO_CUSTOMER_SUCCEEDED: $it")
                }, onError = {
                    Timber.tag(TAG).d("CALL_TO_CUSTOMER_FAILED: $it")
                }
            ).addToDisposable()
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
//        const val CALL_TO_CUSTOMER_SUCCEEDED = "CALL_TO_CUSTOMER_SUCCEEDED"
//        const val CALL_TO_CUSTOMER_FAILED = "CALL_TO_CUSTOMER_FAILED"
    }
}