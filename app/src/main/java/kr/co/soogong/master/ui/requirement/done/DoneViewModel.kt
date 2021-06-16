package kr.co.soogong.master.ui.requirement.done

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.domain.usecase.requirement.AskForReviewUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetDoneEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val getDoneEstimationListUseCase: GetDoneEstimationListUseCase,
    private val askForReviewUseCase: AskForReviewUseCase,
) : BaseViewModel() {
    val doneList = ListLiveData<RequirementCard>()

    fun requestList() {
        Timber.tag(TAG).d("requestList: ")
        getDoneEstimationListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    doneList.addAll(it)
                    sendEvent(BADGE_UPDATE, doneList.getItemCount())
                },
                onError = {
                    setAction(REQUEST_LIST_FAILED)
                    doneList.clear()
                }
            ).addToDisposable()
    }

    fun onFilterChange(index: Int) {
        Timber.tag(TAG).d("onFilterChange: $index")

        getDoneEstimationListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    doneList.clear()
                    when (index) {
                        1 -> {
                            doneList.addAll(list.filter { it.status == RequirementStatus.Done.toString() })
                        }
                        2 -> {
                            doneList.addAll(list.filter { it.status == RequirementStatus.Closed.toString() })
                        }
                        3 -> {
                            doneList.addAll(list.filter { it.status == RequirementStatus.Canceled.toString() || it.status == RequirementStatus.CanceledByClient.toString() || it.status == RequirementStatus.CanceledByMaster.toString() })
                        }
                        else -> {
                            doneList.addAll(list)
                        }
                    }
                },
                onError = {
                    setAction(REQUEST_LIST_FAILED)
                    doneList.clear()
                }
            ).addToDisposable()
    }

    fun askForReview(requirementId: Int) {
        Timber.tag(TAG).d("askForReview: ")
        askForReviewUseCase(requirementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
                    setAction(ASK_FOR_REVIEW_SUCCEEDED)
                },
                onError = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                    setAction(ASK_FOR_REVIEW_FAILED)
                }).addToDisposable()
    }

    companion object {
        private const val TAG = "DoneViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val REQUEST_LIST_FAILED = "REQUEST_LIST_FAILED"
        const val ASK_FOR_REVIEW_SUCCEEDED = "ASK_FOR_REVIEW_SUCCEEDED"
        const val ASK_FOR_REVIEW_FAILED = "ASK_FOR_REVIEW_FAILED"
    }
}