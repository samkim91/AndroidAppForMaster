package kr.co.soogong.master.ui.requirement.action.end

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.requirement.Requirement
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveRepairUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EndRepairViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveRepairUseCase: SaveRepairUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val requirementId =
        EndRepairActivityHelper.getRequirementIdFromSavedState(savedStateHandle)

    private val _requirement = MutableLiveData<Requirement>()

    val actualPrice = MutableLiveData<String>()
    val actualDate = MutableLiveData(Calendar.getInstance())
    val includingVat = MutableLiveData(false)

    init {
        requestRequirement()
    }

    fun requestRequirement() {
        Timber.tag(TAG).d("requestRequirement: $requirementId")
        getRequirementUseCase(requirementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG)
                        .d("requestRequirement successfully: $it")
                    _requirement.value = it
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirement failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveRepair() {
        Timber.tag(TAG).d("endRepair: ${actualDate.value}/${actualPrice.value}")

        saveRepairUseCase(
            RepairDto(
                requirementToken = _requirement.value?.token,
                estimationId = _requirement.value?.estimationDto?.id,
                actualDate = actualDate.value?.time,
                actualPrice = actualPrice.value?.replace(",", "")?.toInt(),
                includingVat = includingVat.value,
                warrantyDueDate = actualDate.value?.let { // TODO: 2021/06/23 추후 백엔드에서 하는 것으로 수정 필요...
                    val warrantyDate = it
                    warrantyDate.add(Calendar.YEAR, 1)
                    warrantyDate.time
                },
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("END_REPAIR_SUCCESSFULLY: $it")
                    setAction(END_REPAIR_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("END_REPAIR_FAILED: $it")
                    setAction(REQUEST_FAILED)
                }).addToDisposable()
    }

    companion object {
        private const val TAG = "EndRepairViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val END_REPAIR_SUCCESSFULLY = "END_REPAIR_SUCCESSFULLY"
    }
}