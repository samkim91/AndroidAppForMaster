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

    fun requestList() {
        Timber.tag(TAG).d("requestList: ")
        getDoneEstimationListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestList successfully: $it")
                    _doneList.postValue(it)
                    sendEvent(BADGE_UPDATE, it.count())
                },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    setAction(REQUEST_LIST_FAILED)
                    _doneList.postValue(emptyList())
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
                    when (index) {
                        1 -> {
                            _doneList.postValue(list.filter { it.status == RequirementStatus.Done })
                        }
                        2 -> {
                            _doneList.postValue(list.filter { it.status == RequirementStatus.Closed })
                        }
                        3 -> {
                            _doneList.postValue(list.filter { it.status == RequirementStatus.Canceled || it.status == RequirementStatus.CanceledByClient || it.status == RequirementStatus.CanceledByMaster })
                        }
                        else -> {
                            _doneList.postValue(list)
                        }
                    }
                },
                onError = {
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
                scheduledDate = null,
                actualDate = null,
                actualPrice = null,
                warrantyDueDate = null,
                requestReviewYn = true,
                canceledYn = null,
                canceledReason = null,
                description = null,
                review = null,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
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