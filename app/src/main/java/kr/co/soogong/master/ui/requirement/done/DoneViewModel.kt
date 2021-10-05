package kr.co.soogong.master.ui.requirement.done

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.Canceled
import kr.co.soogong.master.data.model.requirement.Closed
import kr.co.soogong.master.data.model.requirement.Done
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.doneCodes
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
                1 -> listOf(Done.code)
                2 -> listOf(Closed.code)
                3 -> listOf(Canceled.code)
                else -> doneCodes
            }, index.value == 3
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Timber.tag(TAG).d("requestList successfully: ")
                    if (index.value == 0) sendEvent(BADGE_UPDATE, it.count())
                    requirements.postValue(it)
                },
                onComplete = { },
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