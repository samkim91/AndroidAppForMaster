package kr.co.soogong.master.ui.requirement.progress

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.data.model.requirement.RequirementStatus.Companion.progressCodes
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getRequirementCardsUseCase: GetRequirementCardsUseCase,
    getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
    callToClientUseCase: CallToClientUseCase,
    requestReviewUseCase: RequestReviewUseCase,
    updateRequestMeasureYnUseCase: UpdateRequestMeasureYnUseCase,
) : RequirementViewModel(getMasterSimpleInfoUseCase, callToClientUseCase, requestReviewUseCase, updateRequestMeasureYnUseCase) {

    override fun requestList() {
        Timber.tag(TAG).d("requestList: ${index.value}")

        getRequirementCardsUseCase(
            when (index.value) {
                1 -> listOf(RequirementStatus.Measuring.code)
                2 -> listOf(RequirementStatus.Measured.code)
                3 -> listOf(RequirementStatus.Repairing.code)
                4 -> listOf(RequirementStatus.RequestFinish.code)
                else -> progressCodes
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
        private const val TAG = "ProgressViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}