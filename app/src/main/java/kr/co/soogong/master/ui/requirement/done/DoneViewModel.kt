package kr.co.soogong.master.ui.requirement.done

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.model.requirement.RequirementStatus.Companion.doneCodes
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val getRequirementCardsUseCase: GetRequirementCardsUseCase,
    getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
    callToClientUseCase: CallToClientUseCase,
    requestReviewUseCase: RequestReviewUseCase,
    updateRequestMeasureYnUseCase: UpdateRequestMeasureYnUseCase,
) : RequirementViewModel(getMasterSimpleInfoUseCase, callToClientUseCase, requestReviewUseCase, updateRequestMeasureYnUseCase) {

    override fun requestList() {
        Timber.tag(TAG).d("onFilterChange: ${index.value}")

        getRequirementCardsUseCase(
            when (index.value) {
                1 -> listOf(RequirementStatus.Done.code)
                2 -> listOf(RequirementStatus.Closed.code)
                3 -> listOf(RequirementStatus.Canceled.code)
                else -> doneCodes
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestList successfully: ")
                    if (index.value == 0) sendEvent(BADGE_UPDATE, it.count())
                    requirements.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    requirements.postValue(emptyList())
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "DoneViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}