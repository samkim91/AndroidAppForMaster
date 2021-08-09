package kr.co.soogong.master.ui.requirement.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovedStatusUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetReceivedRequirementListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    val getMasterApprovedStatusUseCase: GetMasterApprovedStatusUseCase,
    private val getReceivedRequirementListUseCase: GetReceivedRequirementListUseCase
) : BaseViewModel() {
    private val _masterApprovedStatus = MutableLiveData<String>()
    val masterApprovedStatus: LiveData<String>
        get() = _masterApprovedStatus

    private val _receivedList = MutableLiveData<List<RequirementCard>>()
    val receivedList: LiveData<List<RequirementCard>>
        get() = _receivedList

    private val _index = MutableLiveData<Int>()

    fun onFilterChange(index: Int) {
        Timber.tag(TAG).d("onFilterChange: $index")
        _index.value = index
        requestList()
    }

    fun requestList() {
        Timber.tag(TAG).d("requestList: ${_index.value}")

        getReceivedRequirementListUseCase(
            when(_index.value){
                1 -> listOf(RequirementStatus.Requested.toCode())
                2 -> listOf(RequirementStatus.Estimated.toCode())
                else -> RequirementStatus.getReceivedCodes()
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestList successfully: $it")
                    if(_index.value == 0 || _index.value == null) sendEvent(BADGE_UPDATE, it.count())
                    if(_index.value == 2) {
                        _receivedList.postValue(it.filter { list -> list.status == RequirementStatus.Estimated })
                    } else {
                        _receivedList.postValue(it)
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    _receivedList.postValue(emptyList())
                }
            ).addToDisposable()
    }

    fun requestMasterApprovedStatus() {
        Timber.tag(TAG).d("requestMasterApprovedStatus: ")
        _masterApprovedStatus.value = getMasterApprovedStatusUseCase()
    }

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val REQUEST_LIST_FAILED = "REQUEST_LIST_FAILED"
    }
}