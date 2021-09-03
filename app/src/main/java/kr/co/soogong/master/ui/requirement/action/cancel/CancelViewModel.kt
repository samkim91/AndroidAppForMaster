package kr.co.soogong.master.ui.requirement.action.cancel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveEstimationUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveRepairUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.CancelActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CancelViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveRepairUseCase: SaveRepairUseCase,
    private val saveEstimationUseCase: SaveEstimationUseCase,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val requirementId = CancelActivityHelper.getRequirementIdFromSavedState(savedStateHandle)

    private val _requirement = MutableLiveData<RequirementDto>()
    val requirement: LiveData<RequirementDto>
        get() = _requirement

    val canceledCode = MutableLiveData("")
    val canceledDescription = MutableLiveData("")

    fun requestRequirement() {
        Timber.tag(TAG).d("requestRequirement: $requirementId")
        getRequirementUseCase(requirementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
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

    fun saveEstimation() {
        Timber.tag(TAG).d("saveEstimation: ")
        saveEstimationUseCase(
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
                    Timber.tag(TAG).d("Canceled Successfully: $it")
                    setAction(CANCEL_ESTIMATION_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("Canceled Failed: $it")
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