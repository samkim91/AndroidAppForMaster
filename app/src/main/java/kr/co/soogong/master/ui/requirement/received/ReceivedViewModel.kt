package kr.co.soogong.master.ui.requirement.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.Estimated
import kr.co.soogong.master.data.model.requirement.Requested
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovedStatusUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.requirement.receivedCodes
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    val getMasterApprovedStatusUseCase: GetMasterApprovedStatusUseCase,
    private val getRequirementCardsUseCase: GetRequirementCardsUseCase,
) : BaseViewModel() {
    private val _masterApprovedStatus = MutableLiveData<String>()
    val masterApprovedStatus: LiveData<String>
        get() = _masterApprovedStatus

    private val _receivedList = MutableLiveData<List<RequirementCard>>()
    val receivedList: LiveData<List<RequirementCard>>
        get() = _receivedList

    private val _index = MutableLiveData(0)

    fun requestList() {
        Timber.tag(TAG).d("requestList: $_index")

        getRequirementCardsUseCase(
            when (_index.value) {
                1, 3 -> listOf(Requested.code)
                2 -> listOf(Estimated.code)
                else -> receivedCodes
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Timber.tag(TAG).d("requestList onNext: $it")
                    if (_index.value == 0) sendEvent(BADGE_UPDATE, it.count())
                    when (_index.value) {
                        2 -> _receivedList.postValue(it.filter { requirementCard -> requirementCard.status == Estimated })
                        3 -> _receivedList.postValue(it.filter { requirementCard -> requirementCard.estimationDto?.requestConsultingYn == true })
                        else -> _receivedList.postValue(it)
                    }
                },
                onComplete = { },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    _receivedList.postValue(emptyList())
                },
            ).addToDisposable()
    }

    fun onFilterChange(index: Int) {
        _index.value = index
        requestList()
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