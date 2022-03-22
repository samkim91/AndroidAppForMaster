package kr.co.soogong.master.presentation.ui.requirement.action.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.estimation.SaveMasterMemoDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.GetMasterSettingsUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.RespondToMeasureUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.SaveEstimationUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.SaveMasterMemoUseCase
import kr.co.soogong.master.domain.usecase.requirement.review.RequestReviewUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewRequirementViewModel @Inject constructor(
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveEstimationUseCase: SaveEstimationUseCase,
    private val respondToMeasureUseCase: RespondToMeasureUseCase,
    private val callToClientUseCase: CallToClientUseCase,
    private val requestReviewUseCase: RequestReviewUseCase,
    private val getMasterSettingsUseCase: GetMasterSettingsUseCase,
    private val saveMasterMemoUseCase: SaveMasterMemoUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    // Note : activity 에서 viewModel 로 데이터 넘기는 법. savedStateHandle 에서 가져온다.
    val requirementId =
        MutableLiveData(ViewRequirementActivityHelper.getRequirementIdFromSavedState(
            savedStateHandle))

    private val _requirement = MutableLiveData<Requirement>()
    val requirement: LiveData<Requirement>
        get() = _requirement

    val masterMemo = MutableLiveData<String>()

    init {
        requestMasterSimpleInfo()
    }

    fun requestRequirement() {
        Timber.tag(TAG).d("requestRequirement: ${requirementId.value}")
        getRequirementUseCase(requirementId.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirement successfully: $it")
                    when (it.estimation.masterResponseCode) {
                        CodeTable.REFUSED, CodeTable.EXPIRED -> {
                            Timber.tag(TAG).d("invalid requirement: ")
                            setAction(INVALID_REQUIREMENT)
                        }
                        CodeTable.ACCEPTED, CodeTable.DEFAULT -> {
                            _requirement.value = it
                            masterMemo.value = it.estimation.masterMemo
                        }
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirement failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun refuseToEstimate() {
        Timber.tag(TAG).d("refuseToEstimate: ")
        saveEstimationUseCase(
            estimationDto = EstimationDto(
                id = requirement.value?.estimation?.id,
                token = requirement.value?.estimation?.token,
                requirementId = requirement.value?.estimation?.requirementId,
                masterId = requirement.value?.estimation?.masterId,
                masterResponseCode = CodeTable.REFUSED.code,
                typeCode = null,
                price = null,
                description = null,
                estimationPrices = null,
                repair = null,
                createdAt = null,
                updatedAt = null,
            ), imageUris = null
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("refuseToEstimate is successful: $it")
                    setAction(REFUSE_TO_ESTIMATE_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).w("refuseToEstimate is failed: $it")
                    setAction(REQUEST_FAILED)
                }).addToDisposable()
    }

    fun respondToMeasure() {
        Timber.tag(TAG).d("respondToMeasure: ")
        respondToMeasureUseCase(
            estimationDto = EstimationDto(
                id = requirement.value?.estimation?.id,
                token = requirement.value?.estimation?.token,
                requirementId = requirement.value?.estimation?.requirementId,
                masterId = requirement.value?.estimation?.masterId,
                masterResponseCode = CodeTable.ACCEPTED.code,
                typeCode = null,
                price = null,
                createdAt = null,
                updatedAt = null,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("acceptToMeasure is successful: ")
                    requestRequirement()
                    setAction(ACCEPT_TO_MEASURE_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).w("acceptToMeasure is failed: $it")
                    setAction(REQUEST_FAILED)
                }).addToDisposable()
    }

    fun callToClient() {
        Timber.tag(TAG).d("callToClient: ")
        _requirement.value?.estimation?.id?.let { estimationId ->
            callToClientUseCase(
                estimationId = estimationId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                    },
                    onError = {
                        Timber.tag(TAG).d("callToClient failed: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }

        sendEvent(CALL_TO_CLIENT, requirement.value?.phoneNumber!!)
    }

    fun askForReview() {
        Timber.tag(TAG).d("askForReview: ")

        viewModelScope.launch {
            try {
                requestReviewUseCase(_requirement.value?.estimation?.repair?.id!!)
            } catch (e: Exception) {
                setAction(REQUEST_FAILED)
            }

            requestRequirement()
            setAction(ASK_FOR_REVIEW_SUCCESSFULLY)
        }
    }

    private fun requestMasterSimpleInfo() {
        Timber.tag(TAG).d("requestMasterSimpleInfo: ")
        getMasterSettingsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { masterSettings ->
                    Timber.tag(TAG).d("requestMasterSimpleInfo successful: $masterSettings")
                    masterSettings.approvedStatus.let {
                        when (it) {
                            CodeTable.NOT_APPROVED.code -> setAction(NOT_APPROVED_MASTER)
                            CodeTable.REQUEST_APPROVE.code -> setAction(REQUEST_APPROVE_MASTER)
                        }
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun showMenuBottomSheetDialog() {
        Timber.tag(TAG).d("showMenuBottomSheetDialog: ")
        setAction(SHOW_MEMO_BOTTOM_SHEET_DIALOG)
    }

    fun saveMasterMemo() {
        Timber.tag(TAG).d("saveMasterMemo: ")

        viewModelScope.launch {
            try {
                saveMasterMemoUseCase(
                    _requirement.value?.estimation?.token!!,
                    SaveMasterMemoDto(
                        getMasterUidFromSharedUseCase(),
                        masterMemo.value ?: ""
                    )
                )

                Timber.tag(TAG).d("saveMasterMemo successfully: ")
                requestRequirement()
            } catch (e: Exception) {
                Timber.tag(TAG).d("saveMasterMemo failed: $e")
                setAction(REQUEST_FAILED)
            }
        }
    }

    companion object {
        private val TAG = ViewRequirementViewModel::class.java.simpleName
        const val REFUSE_TO_ESTIMATE_SUCCESSFULLY = "REFUSE_TO_ESTIMATE_SUCCESSFULLY"
        const val INVALID_REQUIREMENT = "INVALID_REQUIREMENT"
        const val ACCEPT_TO_MEASURE_SUCCESSFULLY = "RESPOND_TO_MEASURE_SUCCESSFULLY"
        const val CALL_TO_CLIENT = "CALL_TO_CLIENT"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val NOT_APPROVED_MASTER = "NOT_APPROVED_MASTER"
        const val REQUEST_APPROVE_MASTER = "REQUEST_APPROVE_MASTER"
        const val SHOW_MEMO_BOTTOM_SHEET_DIALOG = "SHOW_MEMO_BOTTOM_SHEET_DIALOG"
    }
}