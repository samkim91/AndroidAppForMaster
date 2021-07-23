package kr.co.soogong.master.ui.requirement.action.cancel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveRepairUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.CancelRepairActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CancelRepairViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveRepairUseCase: SaveRepairUseCase,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val requirementId = CancelRepairActivityHelper.getRequirementIdFromSavedState(savedStateHandle)
    private val _requirement = MutableLiveData<RequirementDto>()

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
                    setAction(CANCEL_ESTIMATION_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "CancelEstimationViewModel"
        const val CANCEL_ESTIMATION_SUCCESSFULLY = "CANCEL_ESTIMATION_SUCCESSFULLY"
        const val CANCEL_ESTIMATION_FAILED = "CANCEL_ESTIMATION_FAILED"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}