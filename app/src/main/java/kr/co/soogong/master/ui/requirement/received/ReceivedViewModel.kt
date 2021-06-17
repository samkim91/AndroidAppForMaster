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

    fun requestList() {
        Timber.tag(TAG).d("requestList: ")
        getReceivedRequirementListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestList successfully: $it")
                    _receivedList.postValue(it)
                    sendEvent(BADGE_UPDATE, it.count())
                },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    setAction(REQUEST_LIST_FAILED)
                    _receivedList.postValue(emptyList())
                }
            ).addToDisposable()
    }

    fun onFilterChange(index: Int) {
        Timber.tag(TAG).d("onFilterChange: $index")

        getReceivedRequirementListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    when (index) {
                        1 -> {
                            _receivedList.postValue(list.filter { it.status == RequirementStatus.Requested.toString() })
                        }
                        2 -> {
                            _receivedList.postValue(list.filter { it.status == RequirementStatus.Estimated.toString() })
                        }
                        else -> {
                            _receivedList.postValue(list)
                        }
                    }
                },
                onError = {
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