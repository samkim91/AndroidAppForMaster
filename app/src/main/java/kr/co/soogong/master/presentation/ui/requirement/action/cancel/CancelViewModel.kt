package kr.co.soogong.master.presentation.ui.requirement.action.cancel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.entity.common.CodeDto
import kr.co.soogong.master.data.entity.requirement.estimation.RefusingMeasureDto
import kr.co.soogong.master.data.entity.requirement.repair.CancelRepairDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.RefuseToMeasureUseCase
import kr.co.soogong.master.domain.usecase.requirement.repair.CancelRepairUseCase
import kr.co.soogong.master.domain.usecase.requirement.repair.GetCanceledReasonsUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.uihelper.requirment.action.CancelActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CancelViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val cancelRepairUseCase: CancelRepairUseCase,
    private val refuseToMeasureUseCase: RefuseToMeasureUseCase,
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

        viewModelScope.launch {
            try {
                cancelRepairUseCase(
                    CancelRepairDto(
                        requirementToken = _requirement.value?.token!!,
                        estimationId = _requirement.value?.estimation?.id!!,
                        canceledYn = true,
                        canceledCode = canceledCode.value!!,
                        canceledDescription = canceledDescription.value!!,
                    )
                )

                Timber.tag(TAG).d("Canceled Successfully: ")
                setAction(CANCEL_ESTIMATION_SUCCESSFULLY)
            } catch (e: Exception) {
                Timber.tag(TAG).d("Canceled Failed: $e")
                setAction(REQUEST_FAILED)
            }
        }
    }

    fun refuseToMeasure() {
        Timber.tag(TAG).d("refuseToMeasure: ")
        refuseToMeasureUseCase(
            refusingMeasureDto = RefusingMeasureDto(
                id = _requirement.value?.estimation?.id!!,
                token = _requirement.value?.estimation?.token!!,
                masterId = _requirement.value?.estimation?.masterId!!,
                masterResponseCode = CodeTable.REFUSED.code,
                refuseCode = canceledCode.value!!,
                refuseDescription = canceledDescription.value,
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