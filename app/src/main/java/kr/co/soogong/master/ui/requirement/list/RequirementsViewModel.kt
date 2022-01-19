package kr.co.soogong.master.ui.requirement.list

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.common.EndlessScrollableViewModel
import kr.co.soogong.master.ui.requirement.RequirementViewModelAggregate
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class RequirementsViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : EndlessScrollableViewModel() {

    val requirements = ListLiveData<RequirementCard>()
    val isEmptyList = MutableLiveData(false)

    // TODO: 2022/01/13 initList() 및 loadMoreItems() 를 여기로 뺴고, 공통적으로 실행하는 함수를 선언해보자.


    open fun requestRequirements() {}

    fun callToClient(requirementId: Int) {
        Timber.tag(TAG).d("callToCustomer: $requirementId")
        requirements.value?.find { it.id == requirementId }?.estimationId?.let { estimationId ->
            requirementViewModelAggregate.callToClientUseCase(estimationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                    },
                    onError = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun askForReview(requirementCard: RequirementCard?) {
        Timber.tag(TAG).d("askForReview: ")
        requirementViewModelAggregate.requestReviewUseCase(
            RepairDto(
                id = requirementCard?.repairId,
                requirementToken = requirementCard?.token,
                estimationId = requirementCard?.estimationId,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
                    setAction(ASK_FOR_REVIEW_SUCCESSFULLY)
                    initList()
                },
                onError = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "RequirementViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
    }
}