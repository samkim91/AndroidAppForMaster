package kr.co.soogong.master.ui.requirement.action.end

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.EndEstimate
import kr.co.soogong.master.domain.usecase.requirement.EndRepairUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EndRepairViewModel @Inject constructor(
    private val endRepairUseCase: EndRepairUseCase,
) : BaseViewModel() {
    fun endRepair(estimationId: Int, endEstimate: EndEstimate) {
        Timber.tag(TAG).d("doOnFinish: ")

        // 키코드, 브랜치키코드, 시공일자, 최종시공액 .. 기존 API에서는 주소도 받는데 유지필요하려나?
        endRepairUseCase(estimationId = estimationId, endEstimate = endEstimate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess =
                {
                    Timber.tag(TAG).d("END_REPAIR_SUCCESSFULLY: $it")
                    setAction(END_REPAIR_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("END_REPAIR_FAILED: $it")
                    setAction(END_REPAIR_FAILED)
                }).addToDisposable()
    }

    companion object {
        private const val TAG = "EndEstimateViewModel"
        const val END_REPAIR_SUCCESSFULLY = "END_REPAIR_SUCCESSFULLY"
        const val END_REPAIR_FAILED = "END_REPAIR_FAILED"
    }

}