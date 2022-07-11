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
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.requirement.estimation.AcceptingMeasureDto
import kr.co.soogong.master.data.entity.requirement.estimation.SaveMasterMemoDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.requirement.Requirement
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.domain.usecase.profile.GetMasterSettingsUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.AcceptToMeasureUseCase
import kr.co.soogong.master.domain.usecase.requirement.estimation.IncreaseCallCountUseCase
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
    private val acceptToMeasureUseCase: AcceptToMeasureUseCase,
    private val increaseCallCountUseCase: IncreaseCallCountUseCase,
    private val requestReviewUseCase: RequestReviewUseCase,
    private val getMasterSettingsUseCase: GetMasterSettingsUseCase,
    private val saveMasterMemoUseCase: SaveMasterMemoUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
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

    fun showAcceptingEstimation() {
        Timber.tag(TAG).d("showAcceptingEstimation: ")
        setAction(SHOW_ACCEPTING_ESTIMATION)
    }

    fun showCallToClient() {
        Timber.tag(TAG).d("showCallToClient: ")
        setAction(SHOW_CALL_TO_CLIENT)
    }

    fun acceptToMeasure() {
        Timber.tag(TAG).d("acceptToMeasure: ")

        acceptToMeasureUseCase(
            acceptingMeasureDto = AcceptingMeasureDto(
                id = requirement.value?.estimation?.id!!,
                token = requirement.value?.estimation?.token!!,
                masterId = requirement.value?.estimation?.masterId!!,
                masterResponseCode = CodeTable.ACCEPTED.code
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("acceptToMeasure is successful: ")
                    callToClient()
                },
                onError = {
                    Timber.tag(TAG).w("acceptToMeasure is failed: $it")
                    it.message?.run {
                        if (this.contains("HTTP 400")) sendEvent(RESULT_OF_ACCEPTING_MEASURE,
                            R.string.accepted_requirement_by_others)
                        else sendEvent(RESULT_OF_ACCEPTING_MEASURE, R.string.error_message_of_request_failed)
                    }
                }).addToDisposable()
    }

    fun callToClient() {
        Timber.tag(TAG).d("callToClient: ")
        _requirement.value?.estimation?.id?.let { estimationId ->
            increaseCallCountUseCase(
                estimationId = estimationId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                        sendEvent(RESULT_OF_ACCEPTING_MEASURE, _requirement.value?.phoneNumber!!)
                    },
                    onError = {
                        Timber.tag(TAG).e("callToClient failed: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
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

    fun showCallCenterDescription() {
        Timber.tag(TAG).d("showCallCenterDescription: ")
        setAction(SHOW_CALL_CENTER_DESCRIPTION)
    }

    fun showMasterMemo() {
        Timber.tag(TAG).d("showMasterMemo: ")
        setAction(SHOW_MASTER_MEMO)
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
        const val SHOW_ACCEPTING_ESTIMATION = "SHOW_ACCEPTING_ESTIMATION"
        const val SHOW_CALL_TO_CLIENT = "SHOW_CALL_TO_CLIENT"
        const val RESULT_OF_ACCEPTING_MEASURE = "RESULT_OF_ACCEPT_TO_MEASURE"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val NOT_APPROVED_MASTER = "NOT_APPROVED_MASTER"
        const val REQUEST_APPROVE_MASTER = "REQUEST_APPROVE_MASTER"
        const val SHOW_CALL_CENTER_DESCRIPTION = "SHOW_CALL_CENTER_DESCRIPTION"
        const val SHOW_MASTER_MEMO = "SHOW_MASTER_MEMO"
    }
}