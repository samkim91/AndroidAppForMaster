package kr.co.soogong.master.ui.requirement.progress

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.domain.usecase.requirement.CallToCustomerUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetProgressEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getProgressEstimationListUseCase: GetProgressEstimationListUseCase,
    private val callToCustomerUseCase: CallToCustomerUseCase,
) : BaseViewModel() {
    val progressList = ListLiveData<RequirementCard>()

    fun requestList() {
        Timber.tag(TAG).d("requestList: ")
        getProgressEstimationListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    progressList.addAll(it)
                    sendEvent(BADGE_UPDATE, progressList.getItemCount())
                },
                onError = {
                    setAction(REQUEST_LIST_FAILED)
                    progressList.clear()
                }
            ).addToDisposable()
    }

    fun onFilterChange(index: Int) {
        Timber.tag(TAG).d("onFilterChange: $index")

        getProgressEstimationListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    progressList.clear()
                    when (index) {
                        1 -> {
                            progressList.addAll(list.filter { it.status == RequirementStatus.Repairing.toString() })
                        }
                        2 -> {
                            progressList.addAll(list.filter { it.status == RequirementStatus.RequestFinish.toString() })
                        }
                        else -> {
                            progressList.addAll(list)
                        }
                    }
                },
                onError = {
                    setAction(REQUEST_LIST_FAILED)
                    progressList.clear()
                }
            ).addToDisposable()
    }


    fun callToCustomer(estimationId: Int, phoneNumber: String) {
        Timber.tag(TAG).d("callToCustomer: $estimationId / $phoneNumber")
        callToCustomerUseCase(estimationId)
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

    companion object {
        private const val TAG = "ProgressViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val UPDATE_LIST = "UPDATE_LIST"
        const val REQUEST_LIST_FAILED = "REQUEST_LIST_FAILED"
//        const val CALL_TO_CUSTOMER_SUCCEEDED = "CALL_TO_CUSTOMER_SUCCEEDED"
//        const val CALL_TO_CUSTOMER_FAILED = "CALL_TO_CUSTOMER_FAILED"
    }
}