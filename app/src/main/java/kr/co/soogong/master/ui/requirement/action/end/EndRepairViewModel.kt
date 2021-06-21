package kr.co.soogong.master.ui.requirement.action.end

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.domain.usecase.requirement.SaveRepairUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.EndRepairActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EndRepairViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveRepairUseCase: SaveRepairUseCase,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    //    private val requirementId = savedStateHandle.get<Bundle>(BUNDLE_KEY)?.getInt(END_REPAIR_REQUIREMENT_INT_KEY)
    private val requirementId =
        EndRepairActivityHelper.getRequirementIdBySaveState(savedStateHandle)

    private val _requirement = MutableLiveData<RequirementDto>()

    val actualPrice = MutableLiveData("")
    val actualDate = MutableLiveData("")

    fun requestRequirement() {
        Timber.tag(TAG).d("requestRequirement: ")
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
                id = null,
                estimationId = _requirement.value?.estimationDto?.id,
                scheduledDate = null,
                actualDate = actualDate.value,
                actualPrice = actualPrice.value?.replace(",", "")?.toInt(),
                warrantyDueDate = null,
                requestReviewYn = null,
                canceledYn = null,
                canceledReason = null,
                description = null,
                review = null,
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
                    setAction(END_REPAIR_FAILED)
                }).addToDisposable()
    }

    companion object {
        private const val TAG = "EndRepairViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val END_REPAIR_SUCCESSFULLY = "END_REPAIR_SUCCESSFULLY"
        const val END_REPAIR_FAILED = "END_REPAIR_FAILED"
    }

}