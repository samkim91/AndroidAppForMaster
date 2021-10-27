package kr.co.soogong.master.ui.requirement.done

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus.Companion.doneStatus
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.RequirementViewModelAggregate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : RequirementViewModel(requirementViewModelAggregate) {

    override fun requestList() {
        Timber.tag(TAG).d("onFilterChange: ${index.value}")

        index.value?.let { _index ->
            requirementViewModelAggregate.getRequirementCardsUseCase(
                if (_index == 0) {
                    doneStatus.map { it.code }
                } else {
                    listOf(doneStatus[_index - 1].code)
                }
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("requestList successfully: ")
                        if (_index == 0) sendEvent(BADGE_UPDATE, it.count())
                        requirements.postValue(it)
                    },
                    onError = {
                        Timber.tag(TAG).d("requestList failed: $it")
                        requirements.postValue(emptyList())
                    }
                ).addToDisposable()
        }
    }

    fun askForReview(requirementCard: RequirementCard?) {
        Timber.tag(TAG).d("askForReview: ")
        requirementViewModelAggregate.requestReviewUseCase(
            RepairDto(
                id = requirementCard?.estimationDto?.repair?.id,
                requirementToken = requirementCard?.token,
                estimationId = requirementCard?.estimationDto?.id,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
                    requestList()
                    setAction(ASK_FOR_REVIEW_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                    setAction(ASK_FOR_REVIEW_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "DoneViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val ASK_FOR_REVIEW_FAILED = "ASK_FOR_REVIEW_FAILED"
    }
}