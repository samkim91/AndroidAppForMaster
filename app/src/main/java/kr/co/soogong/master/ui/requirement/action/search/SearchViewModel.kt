package kr.co.soogong.master.ui.requirement.action.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.requirement.SearchRequirementCardsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRequirementCardsUseCase: SearchRequirementCardsUseCase
) : BaseViewModel() {

    val searchingText = MutableLiveData("")

    private val _spinnerItems = periods
    val spinnerItems: List<String>
        get() = _spinnerItems

    val searchingPeriod = MutableLiveData(0)

    private val _requirements = MutableLiveData<List<RequirementCard>>()
    val requirements: LiveData<List<RequirementCard>>
        get() = _requirements

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
                onNext = {
                    _requirements.postValue(it)
                },
                onError = {
                    setAction(SEARCH_REQUIREMENTS_FAILED)
                },
                onComplete = {
                    Timber.tag(TAG).d("requirements onComplete: ")
                },
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "SearchViewModel"
        const val SEARCH_REQUIREMENTS_FAILED = "SEARCH_REQUIREMENTS_FAILED"
    }
}