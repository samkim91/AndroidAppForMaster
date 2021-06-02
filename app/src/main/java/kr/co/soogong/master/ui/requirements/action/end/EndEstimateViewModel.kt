package kr.co.soogong.master.ui.requirements.action.end

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.estimation.EndEstimate
import kr.co.soogong.master.domain.usecase.requirement.EndEstimateUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EndEstimateViewModel @Inject constructor(
    private val endEstimateUseCase: EndEstimateUseCase,
) : BaseViewModel() {
    fun endRepair(estimationId: String, endEstimate: EndEstimate) {
        Timber.tag(TAG).d("doOnFinish: ")

        // 키코드, 브랜치키코드, 시공일자, 최종시공액 .. 기존 API에서는 주소도 받는데 유지필요하려나?
        endEstimateUseCase(keycode = estimationId, endEstimate = endEstimate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess =
                {
                    Timber.tag(TAG).d("END_ESTIMATE_SUCCEEDED: $it")
                    setAction(END_ESTIMATE_SUCCEEDED)
                },
                onError = {
                    Timber.tag(TAG).d("END_ESTIMATE_FAILED: $it")
                    setAction(END_ESTIMATE_FAILED)
                }).addToDisposable()
    }

    companion object {
        private const val TAG = "EndEstimateViewModel"
        const val END_ESTIMATE_SUCCEEDED = "END_ESTIMATE_SUCCEEDED"
        const val END_ESTIMATE_FAILED = "END_ESTIMATE_FAILED"
    }

}