package kr.co.soogong.master.ui.requirement.done

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.domain.usecase.requirement.GetDoneEstimationListUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveRepairUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val getDoneEstimationListUseCase: GetDoneEstimationListUseCase,
    private val saveRepairUseCase: SaveRepairUseCase,
) : BaseViewModel() {
    private val _doneList = MutableLiveData<List<RequirementCard>>()
    val doneList: LiveData<List<RequirementCard>>
        get() = _doneList

    fun requestList(index: Int = 0) {
        Timber.tag(TAG).d("onFilterChange: $index")

        getDoneEstimationListUseCase(
            when (index) {
                1 -> listOf(RequirementStatus.Done.toCode())
                2 -> listOf(RequirementStatus.Closed.toCode())
                3 -> listOf(
                    // TODO: 2021/06/28 추가해야함 ... request 보낼때 canceledYn을 같이 보내도록..
                )
                else -> listOf(
                    RequirementStatus.Done.toCode(),
                    RequirementStatus.Closed.toCode(),
                    RequirementStatus.Impossible.toCode()
                )
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestList successfully: $it")
                    _doneList.postValue(it)
                    if (index == 0) sendEvent(BADGE_UPDATE, it.count())
                },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    setAction(REQUEST_LIST_FAILED)
                    _doneList.postValue(emptyList())
                }
            ).addToDisposable()
    }

    fun askForReview(requirementCard: RequirementCard?) {
        Timber.tag(TAG).d("askForReview: ")
        saveRepairUseCase(
            RepairDto(
                id = requirementCard?.estimationDto?.repair?.id,
                requirementToken = requirementCard?.token,
                estimationId = requirementCard?.estimationDto?.id,
                requestReviewYn = true,
            )
        )
            .subscribeOn(Schedulers.io())
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
        const val REQUEST_LIST_FAILED = "REQUEST_LIST_FAILED"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val ASK_FOR_REVIEW_FAILED = "ASK_FOR_REVIEW_FAILED"
    }
}