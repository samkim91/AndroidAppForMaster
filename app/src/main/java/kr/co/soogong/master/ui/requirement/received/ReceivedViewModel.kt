package kr.co.soogong.master.ui.requirement.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetReceivedRequirementListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.requirement.progress.ProgressViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    val getMasterApprovalUseCase: GetMasterApprovalUseCase,
    private val getReceivedRequirementListUseCase: GetReceivedRequirementListUseCase
) : BaseViewModel() {
    private val _isApprovedMaster = MutableLiveData<Boolean>(getMasterApprovalUseCase())
    val isApprovedMaster: LiveData<Boolean>
        get() = _isApprovedMaster

    private val _receivedList = MutableLiveData<List<RequirementCard>>()
    val receivedList: LiveData<List<RequirementCard>>
        get() = _receivedList

    private val _index = MutableLiveData<Int>()

    fun onFilterChange(index: Int) {
        Timber.tag(TAG).d("onFilterChange: $index")
        _index.value = index
        requestList()
    }

    fun requestList(index: Int = 0) {
        Timber.tag(TAG).d("requestList: ${_index.value}")

        getReceivedRequirementListUseCase(
            when(_index.value){
                1 -> listOf(RequirementStatus.Requested.toCode())
                2 -> listOf(RequirementStatus.Estimated.toCode())
                else -> listOf(RequirementStatus.Requested.toCode(), RequirementStatus.Estimated.toCode())
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestList successfully: $it")
                    if(_index.value == 0 || _index.value == null) sendEvent(BADGE_UPDATE, it.count())
                    _receivedList.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    setAction(REQUEST_LIST_FAILED)
                    _receivedList.postValue(emptyList())
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val REQUEST_LIST_FAILED = "REQUEST_LIST_FAILED"
    }
}