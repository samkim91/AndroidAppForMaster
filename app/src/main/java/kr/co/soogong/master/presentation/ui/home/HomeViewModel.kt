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

    private val _beforeProgress = MutableLiveData(0)
    val beforeProgress: LiveData<Int>
        get() = _beforeProgress

    private val _processing = MutableLiveData(0)
    val processing: LiveData<Int>
        get() = _processing

    private val _afterProcess = MutableLiveData(0)
    val afterProcess: LiveData<Int>
        get() = _afterProcess

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
            readYns = false,
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
                    _beforeProgress.postValue(responseDto.data?.beforeProcessCount)
                    _processing.postValue(responseDto.data?.processingCount)
                    _afterProcess.postValue(responseDto.data?.afterProcessCount)
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