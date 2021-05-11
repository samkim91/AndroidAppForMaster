package kr.co.soogong.master.ui.requirements.action.view

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.usecase.requirement.AskForReviewUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToCustomerUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetEstimationUseCase
import kr.co.soogong.master.domain.usecase.requirement.RefuseToEstimateUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper.BUNDLE_KEY_ESTIMATION_KEY
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper.EXTRA_KEY_BUNDLE
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewEstimateViewModel @Inject constructor(
    getEstimationUseCase: GetEstimationUseCase,
    private val refuseToEstimateUseCase: RefuseToEstimateUseCase,
    private val callToCustomerUseCase: CallToCustomerUseCase,
    private val askForReviewUseCase: AskForReviewUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    // Note : activity에서 viewModel로 데이터 넘기는 법. savedStateHandle에서 가져온다.
    private val estimationId =
        savedStateHandle.get<Bundle>(EXTRA_KEY_BUNDLE)?.getString(BUNDLE_KEY_ESTIMATION_KEY)!!

    private val _estimation = getEstimationUseCase(estimationId)
    val estimation: LiveData<Estimation?>
        get() = _estimation

    val status: LiveData<EstimationStatus>
        get() = _estimation.map {
            EstimationStatus.getStatus(it?.status, it?.transmissions)
        }

    fun refuseToEstimate(){
        refuseToEstimateUseCase(estimationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("refuseToEstimate is successful: " + it)
                    setAction(REFUSE_TO_ESTIMATE_SUCCEEDED)
                },
                onError = {
                    Timber.tag(TAG).w("refuseToEstimate is failed: " + it)
                    setAction(REFUSE_TO_ESTIMATE_FAILED)
                }).addToDisposable()
    }

    fun callToCustomer(){
        Timber.tag(TAG).d("callToCustomer: ")
        callToCustomerUseCase(estimationId, SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("CALL_TO_CUSTOMER_SUCCEEDED: $it")
                    setAction(CALL_TO_CUSTOMER_SUCCEEDED)
                },
                onError = {
                    Timber.tag(TAG).d("CALL_TO_CUSTOMER_FAILED: $it")
                    setAction(CALL_TO_CUSTOMER_FAILED)
                }
            ).addToDisposable()
    }

    fun askForReview(){
//        Todo.. estimation의 상태에 따라 이후 코드를 진행하는지 조건 추가
//        if(estimation.value?.status == "askedReview") return
        Timber.tag(TAG).d("requestToReview: ")
        askForReviewUseCase(estimationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
                    setAction(ASK_FOR_REVIEW_SUCCEEDED)
                },
                onError = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                    setAction(ASK_FOR_REVIEW_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ViewEstimateViewModel"
        const val REFUSE_TO_ESTIMATE_SUCCEEDED = "REFUSE_TO_ESTIMATE_SUCCEEDED"
        const val REFUSE_TO_ESTIMATE_FAILED = "REFUSE_TO_ESTIMATE_FAILED"
        const val CALL_TO_CUSTOMER_SUCCEEDED = "CALL_TO_CUSTOMER_SUCCEEDED"
        const val CALL_TO_CUSTOMER_FAILED = "CALL_TO_CUSTOMER_FAILED"
        const val ASK_FOR_REVIEW_SUCCEEDED = "ASK_FOR_REVIEW_SUCCEEDED"
        const val ASK_FOR_REVIEW_FAILED = "ASK_FOR_REVIEW_FAILED"

    }
}