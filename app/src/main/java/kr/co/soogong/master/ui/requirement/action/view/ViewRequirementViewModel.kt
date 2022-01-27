package kr.co.soogong.master.ui.requirement.action.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.data.model.profile.Review
import kr.co.soogong.master.data.model.requirement.Requirement
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.data.repository.ProfileRepository
import kr.co.soogong.master.domain.usecase.requirement.*
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewRequirementViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveEstimationUseCase: SaveEstimationUseCase,
    private val respondToMeasureUseCase: RespondToMeasureUseCase,
    private val callToClientUseCase: CallToClientUseCase,
    private val requestReviewUseCase: RequestReviewUseCase,
    private val profileRepository: ProfileRepository,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    // Note : activity 에서 viewModel 로 데이터 넘기는 법. savedStateHandle 에서 가져온다.
    val requirementId =
        MutableLiveData(ViewRequirementActivityHelper.getRequirementIdFromSavedState(
            savedStateHandle))

    private val _requirement = MutableLiveData<Requirement>()
    val requirement: LiveData<Requirement>
        get() = _requirement

    private val _review = MutableLiveData<Review>()
    val review: LiveData<Review>
        get() = _review

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
                    if (it.estimationDto?.masterResponseCode == EstimationResponseCode.REFUSED) {
                        Timber.tag(TAG)
                            .d("invalid requirement: ${it.estimationDto.masterResponseCode}")
                        setAction(INVALID_REQUIREMENT)
                    }
                    _requirement.value = it
                    it.estimationDto?.repair?.review?.let { reviewDto ->
                        _review.value = Review.fromReviewDto(reviewDto)
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
                id = requirement.value?.estimationDto?.id,
                token = requirement.value?.estimationDto?.token,
                requirementId = requirement.value?.estimationDto?.requirementId,
                masterId = requirement.value?.estimationDto?.masterId,
                masterResponseCode = EstimationResponseCode.REFUSED,
                typeCode = null,
                price = null,
                description = null,
                choosenYn = null,
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
                id = requirement.value?.estimationDto?.id,
                token = requirement.value?.estimationDto?.token,
                requirementId = requirement.value?.estimationDto?.requirementId,
                masterId = requirement.value?.estimationDto?.masterId,
                masterResponseCode = EstimationResponseCode.ACCEPTED,
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
                    Timber.tag(TAG).d("acceptToMeasure is successful: $it")
                    setAction(RESPOND_TO_MEASURE_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).w("acceptToMeasure is failed: $it")
                    setAction(REQUEST_FAILED)
                }).addToDisposable()
    }

    fun callToClient() {
        Timber.tag(TAG).d("callToClient: ")
        _requirement.value?.estimationDto?.id?.let { estimationId ->
            callToClientUseCase(
                estimationId = estimationId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                        setAction(CALL_TO_CUSTOMER_SUCCESSFULLY)
                    },
                    onError = {
                        Timber.tag(TAG).d("callToClient failed: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun askForReview() {
        Timber.tag(TAG).d("askForReview: ")
        requestReviewUseCase(
            RepairDto(
                id = _requirement.value?.estimationDto?.repair?.id,
                requirementToken = _requirement.value?.token,
                estimationId = _requirement.value?.estimationDto?.id,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
                    requestRequirement()
                    setAction(ASK_FOR_REVIEW_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    private fun requestMasterSimpleInfo() {
        Timber.tag(TAG).d("requestMasterSimpleInfo: ")
        profileRepository.getMasterSimpleInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { masterDto ->
                    Timber.tag(TAG).d("requestMasterSimpleInfo successful: $masterDto")
                    masterDto?.approvedStatus.let {
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

    companion object {
        private const val TAG = "ViewRequirementViewModel"
        const val REFUSE_TO_ESTIMATE_SUCCESSFULLY = "REFUSE_TO_ESTIMATE_SUCCESSFULLY"
        const val INVALID_REQUIREMENT = "INVALID_REQUIREMENT"
        const val RESPOND_TO_MEASURE_SUCCESSFULLY = "RESPOND_TO_MEASURE_SUCCESSFULLY"
        const val CALL_TO_CUSTOMER_SUCCESSFULLY = "CALL_TO_CUSTOMER_SUCCESSFULLY"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val NOT_APPROVED_MASTER = "NOT_APPROVED_MASTER"
        const val REQUEST_APPROVE_MASTER = "REQUEST_APPROVE_MASTER"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}