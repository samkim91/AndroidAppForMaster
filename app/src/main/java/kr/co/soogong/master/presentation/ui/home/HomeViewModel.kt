package kr.co.soogong.master.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementTotalUseCase
import kr.co.soogong.master.presentation.ui.requirement.RequirementViewModelAggregate
import kr.co.soogong.master.presentation.ui.requirement.list.RequirementsViewModel
import retrofit2.HttpException
import timber.log.Timber
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
    private val getRequirementTotalUseCase: GetRequirementTotalUseCase,
) : RequirementsViewModel(requirementViewModelAggregate) {

    private val _measuringCount = MutableLiveData(0)
    val measuringCount: LiveData<Int>
        get() = _measuringCount

    private val _repairingCount = MutableLiveData(0)
    val repairingCount: LiveData<Int>
        get() = _repairingCount

    private val _doneCount = MutableLiveData(0)
    val doneCount: LiveData<Int>
        get() = _doneCount

    override fun initList() {
        Timber.tag(TAG).d("initList: ")
        requirements.clear()
        resetState()
        requestRequirements()
    }

    override fun loadMoreItems() {
        requestRequirements()
    }

    override fun requestRequirements() {
        Timber.tag(TAG).d("requestRequirementsUnread: ")

        requirementViewModelAggregate.getRequirementCardsUseCase(
            readYn = false,
            offset = offset,
            pageSize = pageSize,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirementsUnread successfully: ")
                    last = it.last
                    totalItemCount = it.numberOfElements
                    requirements.addAll(it.content)
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirementsUnread failed: $it")
                    if ((it as HttpException).code() != HttpURLConnection.HTTP_NOT_FOUND)
                        setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun requestRequirementTotal() {
        Timber.tag(TAG).d("requestRequirementTotal: ")

        getRequirementTotalUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { responseDto ->
                    Timber.tag(TAG).d("requestRequirementTotal successfully: ")
                    _measuringCount.postValue(responseDto.data?.beforeProcessCount)
                    _repairingCount.postValue(responseDto.data?.processingCount)
                    _doneCount.postValue(responseDto.data?.afterProcessCount)
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirementTotal failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "HomeViewModel"

        const val UPDATE_REQUEST_MEASURE_YN_SUCCESSFUL = "UPDATE_REQUEST_MEASURE_YN_SUCCESSFUL"
    }
}