package kr.co.soogong.master.ui.requirement.received

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.Estimated
import kr.co.soogong.master.data.model.requirement.Requested
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import kr.co.soogong.master.ui.requirement.receivedCodes
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
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
                1 -> listOf(Requested.code)
                2 -> listOf(Estimated.code)
                else -> receivedCodes
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Timber.tag(TAG).d("requestList onNext: ")
                    if (index.value == 0) sendEvent(BADGE_UPDATE, it.count())
                    when (index.value) {
                        1 -> requirements.postValue(it.filter { requirementCard -> requirementCard.estimationDto?.requestConsultingYn == false })
                        2 -> requirements.postValue(it.filter { requirementCard -> requirementCard.status == Estimated })
                        3 -> requirements.postValue(it.filter { requirementCard -> requirementCard.estimationDto?.requestConsultingYn == true })
                        else -> requirements.postValue(it)
                    }
                },
                onComplete = { },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    requirements.postValue(emptyList())
                },
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}