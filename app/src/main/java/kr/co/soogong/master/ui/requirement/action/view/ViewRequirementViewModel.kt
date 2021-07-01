package kr.co.soogong.master.ui.requirement.action.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.profile.Review
import kr.co.soogong.master.data.model.requirement.estimation.EstimationResponseCode
import kr.co.soogong.master.domain.usecase.requirement.*
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewRequirementViewModel @Inject constructor(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val saveEstimationUseCase: SaveEstimationUseCase,
    private val callToCustomerUseCase: CallToCustomerUseCase,
    private val saveRepairUseCase: SaveRepairUseCase,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    // Note : activity 에서 viewModel 로 데이터 넘기는 법. savedStateHandle 에서 가져온다.
    private val requirementId =
        ViewRequirementActivityHelper.getRequirementIdFromSavedState(savedStateHandle)

    private val _requirement = MutableLiveData<RequirementDto>()
    val requirement: LiveData<RequirementDto>
        get() = _requirement

    private val _review = MutableLiveData<Review>()
    val review: LiveData<Review>
        get() = _review

    fun requestRequirement() {
        Timber.tag(TAG).d("requestRequirement: $requirementId")
        getRequirementUseCase(requirementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirement successfully: $it")
                    _requirement.value = it
                    it.estimationDto?.repair?.review?.let { reviewDto ->
                        _review.postValue(Review.fromReviewDto(reviewDto))
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirement failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun refuseToEstimate() {
        Timber.tag(TAG).d("requestRequirement: ")
        saveEstimationUseCase(
            estimationDto = EstimationDto(
                id = requirement.value?.estimationDto?.id,
                token = requirement.value?.estimationDto?.token,
                requirementId = requirement.value?.estimationDto?.requirementId,
                masterId = requirement.value?.estimationDto?.masterId,
                masterResponseCode = EstimationResponseCode.REFUSED,
                type = null,
                price = null,
                description = null,
                choosenYn = null,
                estimationPrices = null,
                repair = null,
                createdAt = null,
                updatedAt = null,
            )
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

    // TODO: 2021/06/21 api 나오면 개발해야함
    fun callToCustomer() {
        Timber.tag(TAG).d("callToCustomer: ")
        callToCustomerUseCase(
            requirementId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("CALL_TO_CUSTOMER_SUCCEEDED: $it")
                    setAction(CALL_TO_CUSTOMER_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("CALL_TO_CUSTOMER_FAILED: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun askForReview() {
        Timber.tag(TAG).d("askForReview: ")
        saveRepairUseCase(
            RepairDto(
                id = _requirement.value?.estimationDto?.repair?.id,
                requirementToken = _requirement.value?.token,
                estimationId = _requirement.value?.estimationDto?.id,
                requestReviewYn = true,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
                    setAction(ASK_FOR_REVIEW_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ViewRequirementViewModel"
        const val REFUSE_TO_ESTIMATE_SUCCESSFULLY = "REFUSE_TO_ESTIMATE_SUCCESSFULLY"
        const val CALL_TO_CUSTOMER_SUCCESSFULLY = "CALL_TO_CUSTOMER_SUCCESSFULLY"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}