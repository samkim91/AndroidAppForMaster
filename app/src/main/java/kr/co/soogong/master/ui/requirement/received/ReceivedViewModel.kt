package kr.co.soogong.master.ui.requirement.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.repository.RequirementRepository
import kr.co.soogong.master.domain.usecase.auth.GetMasterSubscriptionPlanUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetReceivedRequirementListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    val getMasterSubscriptionPlanUseCase: GetMasterSubscriptionPlanUseCase,
    private val getReceivedRequirementListUseCase: GetReceivedRequirementListUseCase,
    private val requirementRepository: RequirementRepository

) : BaseViewModel() {
    private val _masterSubscriptionPlan =
        MutableLiveData<String>(getMasterSubscriptionPlanUseCase())
    val masterSubscriptionPlan: LiveData<String>
        get() = _masterSubscriptionPlan

    private val _receivedList = MutableLiveData<List<RequirementCard>>()
    val receivedList: LiveData<List<RequirementCard>>
        get() = _receivedList

    private val _index = MutableLiveData<Int>()

    fun requestList() {
        Timber.tag(TAG).d("requestList: ${_index.value}")

        requirementRepository.getRequirements(RequirementStatus.getReceivedCodes())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Timber.tag(TAG).d("requestList onNext: $it")
                    sendEvent(BADGE_UPDATE, it.count())

                    _receivedList.postValue(it.filter { card ->
                        when(_index.value) {
                            1 -> card.status == RequirementStatus.Requested
                            2 -> card.status == RequirementStatus.Estimated
                            else -> card.status != null
                        }
                    })
                },
                onComplete = { },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    _receivedList.postValue(emptyList())
                },
            ).addToDisposable()
    }

    fun onFilterChange(index: Int) {
        Timber.tag(TAG).d("onFilterChange: $index")

        _index.value = index
        requirementRepository.getRequirementsFromLocalAsCards(
            when (index) {
                1 -> listOf(RequirementStatus.Requested.toCode())
                2 -> listOf(RequirementStatus.Estimated.toCode())
                else -> RequirementStatus.getReceivedCodes()
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestList onNext: $it")
                    _receivedList.postValue(it)
                },
                onError = { },
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val REQUEST_LIST_FAILED = "REQUEST_LIST_FAILED"
    }
}