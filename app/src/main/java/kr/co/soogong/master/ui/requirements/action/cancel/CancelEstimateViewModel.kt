package kr.co.soogong.master.ui.requirements.action.cancel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.estimation.CancelEstimate
import kr.co.soogong.master.domain.usecase.CancelEstimateUseCase
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CancelEstimateViewModel @Inject constructor(
    private val cancelEstimateUseCase: CancelEstimateUseCase,
) : BaseViewModel() {

    fun doCancel(estimationId: String, cancelEstimate: CancelEstimate) {
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