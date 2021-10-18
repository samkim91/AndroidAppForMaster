package kr.co.soogong.master.ui.requirement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateDirectRepairYnUseCase
import kr.co.soogong.master.domain.usecase.profile.UpdateRequestMeasureYnUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class RequirementViewModel @Inject constructor(
    private val getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
    private val callToClientUseCase: CallToClientUseCase,
    private val requestReviewUseCase: RequestReviewUseCase,
    private val updateRequestMeasureYnUseCase: UpdateRequestMeasureYnUseCase,
    private val updateDirectRepairYnUseCase: UpdateDirectRepairYnUseCase,
    // TODO: 2021/10/05 필요한 useCase 가 추가될 때마다, params 가 늘어나는 구조이다. DI 를 활용하는 방법을 찾아보자..
) : BaseViewModel() {

    private val _masterSimpleInfo = MutableLiveData<MasterDto>()
    val masterSimpleInfo: LiveData<MasterDto>
        get() = _masterSimpleInfo

    private val _requestMeasureYn = MutableLiveData(false)
    val requestMeasureYn: LiveData<Boolean>
        get() = _requestMeasureYn

    val requirements = MutableLiveData<List<RequirementCard>>()

    val index = MutableLiveData(0)

    open fun requestList() {}

    fun onFilterChange(index: Int) {
        this.index.value = index
        requestList()
    }

    // 문의탭의 함수들
    fun requestMasterSimpleInfo() {
        Timber.tag(TAG).d("requestMasterSimpleInfo: ")
        getMasterSimpleInfoUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo successful: $it")
                    _masterSimpleInfo.value = it
                    it.requestMeasureYn?.let { boolean -> _requestMeasureYn.value = boolean }
                },
                onError = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    // 진행탭의 함수들
    fun callToClient(requirementId: Int) {
        Timber.tag(TAG).d("callToCustomer: $requirementId")
        requirements.value?.find { it.id == requirementId }?.estimationDto?.id?.let { estimationId ->
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

    // 완료탭의 함수들
    fun askForReview(requirementCard: RequirementCard?) {
        Timber.tag(TAG).d("askForReview: ")
        requestReviewUseCase(
            RepairDto(
                id = requirementCard?.estimationDto?.repair?.id,
                requirementToken = requirementCard?.token,
                estimationId = requirementCard?.estimationDto?.id,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
                    requestList()
                    setAction(ASK_FOR_REVIEW_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                    setAction(ASK_FOR_REVIEW_FAILED)
                }
            ).addToDisposable()
    }

    fun updateRequestMeasureYn(isChecked: Boolean) {
        Timber.tag(TAG)
            .d("updateRequestMeasureYn: ${_masterSimpleInfo.value?.requestMeasureYn} to $isChecked")
        if (_masterSimpleInfo.value?.requestMeasureYn == isChecked) return

        _masterSimpleInfo.value?.uid?.let { uid ->
            updateRequestMeasureYnUseCase(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("updateRequestMeasureYn successful: $it")
                        _masterSimpleInfo.value = it
                    },
                    onError = {
                        Timber.tag(TAG).d("updateRequestMeasureYn failed: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun updateDirectRepairYn(directRepairYn: Boolean) {
        Timber.tag(TAG).d("updateDirectRepairYn to: $directRepairYn")

        updateDirectRepairYnUseCase(
            MasterDto(
                id = _masterSimpleInfo.value?.id,
                directRepairYn = directRepairYn)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("updateDirectRepairYn successful: $it")
                    _masterSimpleInfo.value = it
                },
                onError = {
                    Timber.tag(TAG).d("updateDirectRepairYn failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "RequirementViewModel"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val ASK_FOR_REVIEW_FAILED = "ASK_FOR_REVIEW_FAILED"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}