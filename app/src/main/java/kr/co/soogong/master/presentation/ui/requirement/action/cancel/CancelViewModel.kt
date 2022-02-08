package kr.co.soogong.master.presentation.ui.requirement.action.cancel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.common.CodeDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.entity.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.domain.usecase.requirement.GetCanceledReasonsUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.RespondToMeasureUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveRepairUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.uihelper.requirment.action.CancelActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CancelViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveRepairUseCase: SaveRepairUseCase,
    private val respondToMeasureUseCase: RespondToMeasureUseCase,
    private val canceledReasonsUseCase: GetCanceledReasonsUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val requirementId =
        CancelActivityHelper.getRequirementIdFromSavedState(savedStateHandle)

    private val _requirement = MutableLiveData<Requirement>()
    val requirement: LiveData<Requirement>
        get() = _requirement

    val canceledReasons = MutableLiveData<List<CodeDto>>()

    val canceledCode = MutableLiveData("")
    val canceledDescription = MutableLiveData("")

    init {
        requestCanceledReasons()
        requestRequirement()
    }

    private fun requestCanceledReasons() {
        Timber.tag(TAG).d("requestCanceledReasons: ")
        canceledReasonsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestCanceledReasons successfully: $it")
                    canceledReasons.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("requestCanceledReasons failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    private fun requestRequirement() {
        Timber.tag(TAG).d("requestRequirement: $requirementId")
        getRequirementUseCase(requirementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirement successfully: $it")
                    _requirement.value = it
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirement failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveRepair() {
        Timber.tag(TAG).d("saveRepair: ")
        saveRepairUseCase(
            repairDto = RepairDto(
                requirementToken = _requirement.value?.token,
                estimationId = _requirement.value?.estimationDto?.id,
                canceledYn = true,
                canceledCode = canceledCode.value,
                canceledDescription = canceledDescription.value,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("Canceled Successfully: $it")
                    setAction(CANCEL_ESTIMATION_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("Canceled Failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun respondToMeasure() {
        Timber.tag(TAG).d("respondToMeasure: ")
        respondToMeasureUseCase(
            EstimationDto(
                id = _requirement.value?.estimationDto?.id,
                token = _requirement.value?.estimationDto?.token,
                requirementId = _requirement.value?.estimationDto?.requirementId,
                masterId = _requirement.value?.estimationDto?.masterId,
                typeCode = _requirement.value?.typeCode,
                price = null,
                refuseCode = canceledCode.value,
                refuseDescription = canceledDescription.value,
                masterResponseCode = EstimationResponseCode.REFUSED,
                createdAt = null,
                updatedAt = null,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("respondToMeasure Successfully: $it")
                    setAction(CANCEL_ESTIMATION_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("respondToMeasure Failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "CancelViewModel"
        const val CANCEL_ESTIMATION_SUCCESSFULLY = "CANCEL_SUCCESSFULLY"
        const val CANCEL_MEASURE_SUCCESSFULLY = "CANCEL_MEASURE_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}