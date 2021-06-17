package kr.co.soogong.master.ui.requirement.action.view

import   android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.domain.usecase.requirement.AskForReviewUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToCustomerUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.RefuseToEstimateUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.view.ViewRequirementActivityHelper.BUNDLE_KEY_REQUIREMENT_KEY
import kr.co.soogong.master.uihelper.requirment.action.view.ViewRequirementActivityHelper.EXTRA_KEY_BUNDLE
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewRequirementViewModel @Inject constructor(
    val getRequirementUseCase: GetRequirementUseCase,
    private val refuseToEstimateUseCase: RefuseToEstimateUseCase,
    private val callToCustomerUseCase: CallToCustomerUseCase,
    private val askForReviewUseCase: AskForReviewUseCase,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    // Note : activity에서 viewModel로 데이터 넘기는 법. savedStateHandle에서 가져온다.
    private val requirementId =
        savedStateHandle.get<Bundle>(EXTRA_KEY_BUNDLE)?.getInt(BUNDLE_KEY_REQUIREMENT_KEY)!!

    private val _requirement = MutableLiveData<RequirementDto>()
    val requirement: LiveData<RequirementDto>
        get() = _requirement

    fun requestRequirement() {
        getRequirementUseCase(requirementId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("init successfully: $it")
                    _requirement.value = it
                },
                onError = {
                    Timber.tag(TAG).d("init failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun refuseToEstimate() {
        refuseToEstimateUseCase(requirementId)
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
//        Todo.. estimation의 상태에 따라 이후 코드를 진행하는지 조건 추가
//        if(estimation.value?.status == "askedReview") return
        Timber.tag(TAG).d("requestToReview: ")
        askForReviewUseCase(requirementId)
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