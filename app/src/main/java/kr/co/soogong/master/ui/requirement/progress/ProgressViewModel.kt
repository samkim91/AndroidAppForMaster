package kr.co.soogong.master.ui.requirement.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.*
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementCardsUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.requirement.progressCodes
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getRequirementCardsUseCase: GetRequirementCardsUseCase,
    private val callToClientUseCase: CallToClientUseCase,
) : BaseViewModel() {
    private val _progressList = MutableLiveData<List<RequirementCard>>()
    val progressList: LiveData<List<RequirementCard>>
        get() = _progressList

    private val _index = MutableLiveData(0)

    fun requestList() {
        Timber.tag(TAG).d("requestList: ${_index.value}")

        getRequirementCardsUseCase(
            when (_index.value) {
                1 -> listOf(Measuring.code)
                2 -> listOf(Measured.code)
                3 -> listOf(Repairing.code)
                4 -> listOf(RequestFinish.code)
                else -> progressCodes
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Timber.tag(TAG).d("requestList successfully: $it")
                    if (_index.value == 0) sendEvent(BADGE_UPDATE, it.count())
                    _progressList.postValue(it)
                },
                onComplete = { },
                onError = {
                    Timber.tag(TAG).d("requestList failed: $it")
                    _progressList.postValue(emptyList())
                }
            ).addToDisposable()
    }

    fun onFilterChange(index: Int) {
        Timber.tag(TAG).d("onFilterChange: $index")
        _index.value = index
        requestList()
    }

    fun callToClient(requirementId: Int) {
        Timber.tag(TAG).d("callToCustomer: $requirementId")
        val requirementCard = progressList.value?.find {
            it.id == requirementId
        }

        requirementCard?.estimationDto?.id?.let { estimationId ->
            callToClientUseCase(estimationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                    },
                    onError = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "ProgressViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}