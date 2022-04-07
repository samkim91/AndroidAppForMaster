package kr.co.soogong.master.presentation.ui.requirement.action.end

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.data.entity.requirement.repair.SaveRepairDto
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.repair.SaveRepairUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.uihelper.requirment.action.EndRepairActivityHelper
import kr.co.soogong.master.utility.ListLiveData
import kr.co.soogong.master.utility.extension.formatDateWithDash
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EndRepairViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveRepairUseCase: SaveRepairUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val maxPhoto: Int = 10

    private val requirementId =
        EndRepairActivityHelper.getRequirementIdFromSavedState(savedStateHandle)

    private val _requirement = MutableLiveData<Requirement>()

    val actualPrice = MutableLiveData(0L)
    val actualDate = MutableLiveData(Calendar.getInstance())
    val includingVat = MutableLiveData(false)

    val repairImages = ListLiveData<AttachmentDto>()

    init {
        requestRequirement()
    }

    private fun requestRequirement() {
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

        viewModelScope.launch {
            try {
                setAction(SHOW_LOADING)
                saveRepairUseCase(
                    SaveRepairDto(
                        requirementToken = _requirement.value?.token!!,
                        estimationId = _requirement.value?.estimation?.id!!,
                        actualDate = actualDate.value?.time.formatDateWithDash(),
                        actualPrice = actualPrice.value?.toInt()!!,
                        includingVat = includingVat.value!!,
                        warrantyDueDate = actualDate.value?.let {
                            val warrantyDate = it
                            warrantyDate.add(Calendar.YEAR, 1)
                            warrantyDate.time
                        }.formatDateWithDash(),
                    ),
                    repairImages.value?.map { it.uri!! }
                )

                Timber.tag(TAG).d("END_REPAIR_SUCCESSFULLY: ")
                setAction(END_REPAIR_SUCCESSFULLY)
            } catch (e: Exception) {
                Timber.tag(TAG).d("END_REPAIR_FAILED: $e")
                setAction(REQUEST_FAILED)
            }
        }
    }

    fun startImagePicker() {
        Timber.tag(TAG).d("startImagePicker: ")
        setAction(START_IMAGE_PICKER)
    }

    companion object {
        private val TAG = EndRepairViewModel::class.java.simpleName
        const val END_REPAIR_SUCCESSFULLY = "END_REPAIR_SUCCESSFULLY"
        const val START_IMAGE_PICKER = "START_IMAGE_PICKER"
    }
}