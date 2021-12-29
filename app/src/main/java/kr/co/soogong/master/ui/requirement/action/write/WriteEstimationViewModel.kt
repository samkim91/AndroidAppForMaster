package kr.co.soogong.master.ui.requirement.action.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationPriceDto
import kr.co.soogong.master.data.model.requirement.Requirement
import kr.co.soogong.master.data.model.requirement.estimation.EstimationPriceTypeCode
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.SaveEstimationUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.WriteEstimationActivityHelper
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WriteEstimationViewModel @Inject constructor(
    private val saveEstimationUseCase: SaveEstimationUseCase,
    private val getRequirementUseCase: GetRequirementUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    val estimationTypes = listOf(CodeTable.INTEGRATION, CodeTable.BY_ITEM)
    val estimationType = MutableLiveData(estimationTypes[0])

    private val requirementId =
        WriteEstimationActivityHelper.getRequirementIdFromSavedState(savedStateHandle)

    private val _requirement = MutableLiveData<Requirement>()
    val requirement: LiveData<Requirement>
        get() = _requirement

    val simpleCost = MutableLiveData("")

    val laborCost = MutableLiveData("")
    val materialCost = MutableLiveData("")
    val travelCost = MutableLiveData("")
    val totalCost = MutableLiveData("")

    val includingVat = MutableLiveData(false)

    val description = MutableLiveData("")

    val isSavingTemplate = MutableLiveData(false)
    val estimationImages = ListLiveData<AttachmentDto>()

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
                typeCode = estimationType.value?.code,
                price = when (estimationType.value) {
                    CodeTable.BY_ITEM -> {
                        totalCost.value?.replace(",", "")?.toInt()
                    }
                    else -> {
                        simpleCost.value?.replace(",", "")?.toInt()
                    }
                },
                includingVat = includingVat.value,
                isSavingTemplate = isSavingTemplate.value ?: false,
                description = description.value,
                estimationPrices = when (estimationType.value) {
                    CodeTable.BY_ITEM -> {
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
                createdAt = null,
                updatedAt = null,
            ),
            imageUris = estimationImages.value?.map { it.uri!! },
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setAction(SHOW_LOADING) }
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("sendEstimation onSuccess: ")
                    setAction(SEND_ESTIMATION_SUCCESSFULLY)
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            )
            .addToDisposable()
    }

    fun startEstimationTemplate() {
        Timber.tag(TAG).d("startEstimationTemplate: ")
        setAction(START_ESTIMATION_TEMPLATE)
    }

    fun startImagePicker() {
        Timber.tag(TAG).d("startImagePicker: ")
        setAction(START_IMAGE_PICKER)
    }

    fun startViewRequirement() {
        Timber.tag(TAG).d("startViewRequirement: ")
        setAction(START_VIEW_REQUIREMENT)
    }

    companion object {
        private const val TAG = "WriteEstimationViewModel"

        const val SEND_ESTIMATION_SUCCESSFULLY = "SEND_ESTIMATION_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val START_ESTIMATION_TEMPLATE = "START_ESTIMATION_TEMPLATE"
        const val START_IMAGE_PICKER = "START_IMAGE_PICKER"
        const val START_VIEW_REQUIREMENT = "START_VIEW_REQUIREMENT"

    }
}