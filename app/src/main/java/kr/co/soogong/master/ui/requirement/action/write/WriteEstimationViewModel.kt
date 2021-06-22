package kr.co.soogong.master.ui.requirement.action.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationPriceDto
import kr.co.soogong.master.data.model.requirement.estimation.EstimationPriceTypeCode
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.data.model.requirement.estimation.EstimationTypeCode
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveEstimationUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.WriteEstimationActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WriteEstimationViewModel @Inject constructor(
    private val saveEstimationUseCase: SaveEstimationUseCase,
    private val getRequirementUseCase: GetRequirementUseCase,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val requirementId = WriteEstimationActivityHelper.getRequirementIdBySaveState(savedStateHandle)

    private val _requirement = MutableLiveData<RequirementDto>()
    val requirement: LiveData<RequirementDto>
        get() = _requirement

    val estimationType = MutableLiveData(EstimationTypeCode.INTEGRATION)  // Integration or ByItem

    val simpleCost = MutableLiveData("")

    val laborCost = MutableLiveData("")
    val materialCost = MutableLiveData("")
    val travelCost = MutableLiveData("")
    val totalCost = MutableLiveData("")
    val description = MutableLiveData("")

    fun requestRequirement() {
        Timber.tag(TAG).d("requestRequirement: $requirementId")
        getRequirementUseCase(requirementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _requirement.value = it
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun sendEstimation() {
        Timber.tag(TAG).d("sendEstimation: ")
        saveEstimationUseCase(
            estimationDto = EstimationDto(
                id = requirement.value?.estimationDto?.id,
                token = requirement.value?.estimationDto?.token,
                requirementId = requirement.value?.estimationDto?.requirementId,
                masterId = requirement.value?.estimationDto?.masterId,
                masterResponseCode = EstimationResponseCode.ACCEPTED,
                type = estimationType.value,
                price = when (estimationType.value) {
                    EstimationTypeCode.INTEGRATION -> {
                        simpleCost.value?.replace(",", "")?.toInt()
                    }
                    else -> {
                        totalCost.value?.replace(",", "")?.toInt()
                    }
                },
                description = description.value,
                choosenYn = null,
                estimationPrice = when (estimationType.value) {
                    EstimationTypeCode.BY_ITEM -> {
                        listOf(
                            EstimationPriceDto.inputToEstimationPriceDto(
                                requirement.value?.estimationDto,
                                EstimationPriceTypeCode.LABOR,
                                laborCost.value?.toInt()
                            ),
                            EstimationPriceDto.inputToEstimationPriceDto(
                                requirement.value?.estimationDto,
                                EstimationPriceTypeCode.MATERIAL,
                                materialCost.value?.toInt()
                            ),
                            EstimationPriceDto.inputToEstimationPriceDto(
                                requirement.value?.estimationDto,
                                EstimationPriceTypeCode.TRAVEL,
                                travelCost.value?.toInt()
                            ),
                        )
                    }
                    else -> null
                },
                repair = null,
                createdAt = null,
                updatedAt = null,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    setAction(SEND_ESTIMATION_SUCCESSFULLY)
                },
                onError = {
                    setAction(SEND_ESTIMATION_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "WriteEstimationViewModel"

        const val SEND_ESTIMATION_SUCCESSFULLY = "SEND_ESTIMATION_SUCCESSFULLY"
        const val SEND_ESTIMATION_FAILED = "SEND_ESTIMATION_FAILED"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val SEND_MESSAGE_FAILED = "EMAIL_ERROR"
    }
}