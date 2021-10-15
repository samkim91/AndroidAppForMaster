package kr.co.soogong.master.ui.requirement.action.search

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import kr.co.soogong.master.domain.usecase.requirement.SearchRequirementCardsUseCase
import kr.co.soogong.master.ui.requirement.RequirementViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRequirementCardsUseCase: SearchRequirementCardsUseCase,
    getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
    callToClientUseCase: CallToClientUseCase,
    requestReviewUseCase: RequestReviewUseCase,
    updateRequestMeasureYnUseCase: UpdateRequestMeasureYnUseCase,
) : RequirementViewModel(getMasterSimpleInfoUseCase, callToClientUseCase, requestReviewUseCase, updateRequestMeasureYnUseCase) {

    val searchingText = MutableLiveData("")

    private val _spinnerItems = periods
    val spinnerItems: List<String>
        get() = _spinnerItems

    val searchingPeriod = MutableLiveData(0)

    fun searchRequirements() {
        Timber.tag(TAG).d("searchRequirements: ${searchingText.value} / ${searchingPeriod.value}")
        if (searchingText.value.isNullOrEmpty()) return

        searchRequirementCardsUseCase(
            searchingText = searchingText.value!!,
            searchingPeriod = searchingPeriod.value!!
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    requirements.postValue(it)
                },
                onError = {
                    setAction(SEARCH_REQUIREMENTS_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "SearchViewModel"
        const val SEARCH_REQUIREMENTS_FAILED = "SEARCH_REQUIREMENTS_FAILED"
    }
}