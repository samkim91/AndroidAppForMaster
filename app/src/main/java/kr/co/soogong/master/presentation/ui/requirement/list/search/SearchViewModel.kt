package kr.co.soogong.master.presentation.ui.requirement.list.search

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.entity.common.DropdownItemList
import kr.co.soogong.master.presentation.ui.requirement.RequirementViewModelAggregate
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel
import retrofit2.HttpException
import timber.log.Timber
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : RequirementsViewModel(requirementViewModelAggregate) {

    val spinnerItems: List<Pair<String, Int>> = DropdownItemList.searchingPeriods
    val searchingText = MutableLiveData("")
    val searchingPeriod = MutableLiveData(spinnerItems.first().second)

    val notFound = MutableLiveData(false)

    override fun initList() {
        Timber.tag(TAG).d("initList: ")
        notFound.postValue(false)
        requirements.clear()
        resetState()
        requestRequirements()
    }

    override fun loadMoreItems() {
        Timber.tag(TAG).d("loadMoreItems: ")
        requestRequirements()
    }

    override fun requestRequirements() {
        Timber.tag(TAG).d("requestRequirements: ${searchingText.value} / ${searchingPeriod.value}")
        if (searchingText.value.isNullOrEmpty()) return

        requirementViewModelAggregate.getRequirementCardsUseCase(
            searchingText = searchingText.value,
            searchingPeriod = searchingPeriod.value,
            offset = offset,
            pageSize = pageSize,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirements successfully: ")
                    last = it.last
                    totalItemCount += it.numberOfElements
                    requirements.addAll(it.content)
                },
                onError = {
                    // ???????????? ??????????????? ???????????? ?????? ?????? ????????? ????????? ?????? ?????????, ????????????
                    if ((it as HttpException).code() == HttpURLConnection.HTTP_NOT_FOUND)
                        notFound.postValue(true)
                    else
                        setAction(SEARCH_REQUIREMENTS_FAILED)
                }
            ).addToDisposable()
    }

    fun cancelActivity() {
        Timber.tag(TAG).d("cancelActivity: ")
        setAction(CANCEL_ACTIVITY)
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
        const val SEARCH_REQUIREMENTS_FAILED = "SEARCH_REQUIREMENTS_FAILED"
        const val CANCEL_ACTIVITY = "CANCEL_ACTIVITY"
    }
}