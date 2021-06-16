package kr.co.soogong.master.ui.requirement.action.cancel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.CancelEstimate
import kr.co.soogong.master.domain.usecase.requirement.CancelEstimateUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CancelEstimateViewModel @Inject constructor(
    private val cancelEstimateUseCase: CancelEstimateUseCase,
) : BaseViewModel() {

    fun doCancel(estimationId: Int, cancelEstimate: CancelEstimate) {
        cancelEstimateUseCase(estimationId, cancelEstimate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("Cancelling Estimate Succeeded: $it")
                    setAction(CANCEL_ESTIMATE_SUCCEEDED)
                },
                onError = {
                    Timber.tag(TAG).d("Cancelling Estimate Failed: $it")
                    setAction(CANCEL_ESTIMATE_FAILED)
                }).addToDisposable()
    }

    companion object {
        private const val TAG = "CancelEstimateViewModel"
        const val CANCEL_ESTIMATE_SUCCEEDED = "CANCEL_ESTIMATE_SUCCEEDED"
        const val CANCEL_ESTIMATE_FAILED = "CANCEL_ESTIMATE_FAILED"
    }
}