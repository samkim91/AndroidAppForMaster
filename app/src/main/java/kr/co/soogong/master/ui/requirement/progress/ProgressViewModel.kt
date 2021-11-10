package kr.co.soogong.master.ui.requirement.progress

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementStatus.Companion.progressStatus
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.RequirementViewModelAggregate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : RequirementViewModel(requirementViewModelAggregate) {

    override fun requestList() {
        Timber.tag(TAG).d("requestList: ${filterTabIndex.value}")

        filterTabIndex.value?.let { _index ->
            requirementViewModelAggregate.getRequirementCardsUseCase(
                if (_index == 0) {
                    progressStatus.map { it.code }
                } else {
                    listOf(progressStatus[_index - 1].code)
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

    companion object {
        private const val TAG = "ProgressViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}