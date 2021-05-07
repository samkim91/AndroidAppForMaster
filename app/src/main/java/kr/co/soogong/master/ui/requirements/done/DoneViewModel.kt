package kr.co.soogong.master.ui.requirements.done

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.usecase.requirement.AskForReviewUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetDoneEstimationListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val getDoneEstimationListUseCase: GetDoneEstimationListUseCase,
    private val askForReviewUseCase: AskForReviewUseCase,
) : BaseViewModel() {
    private val _doneList = MutableLiveData<List<RequirementCard>>(emptyList())
    val doneList: LiveData<List<RequirementCard>>
        get() = _doneList

    fun onFilterChange(index: Int) {
        viewModelScope.launch {
            val list = when (index) {
                0 -> {
                    getDoneEstimationListUseCase()
                }
                1 -> {
                    getDoneEstimationListUseCase().filter { it.status == EstimationStatus.Done }
                }
                2 -> {
                    getDoneEstimationListUseCase().filter { it.status == EstimationStatus.Final }
                }
                3 -> {
                    getDoneEstimationListUseCase().filter { it.status == EstimationStatus.Cancel }
                }
                else -> {
                    emptyList()
                }
            }
            _doneList.value = list
            setAction(UPDATE_LIST)
        }
    }

    fun requestList() {
        viewModelScope.launch {
            val list = getDoneEstimationListUseCase()

            sendEvent(BADGE_UPDATE, list.size)

            _doneList.value = list
        }
    }

    fun askForReview(estimationId: String) {
        Timber.tag(TAG).d("askForReview: ")
        askForReviewUseCase(estimationId)
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
        const val UPDATE_LIST = "UPDATE_LIST"
        const val ASK_FOR_REVIEW_SUCCEEDED = "ASK_FOR_REVIEW_SUCCEEDED"
        const val ASK_FOR_REVIEW_FAILED = "ASK_FOR_REVIEW_FAILED"
    }
}