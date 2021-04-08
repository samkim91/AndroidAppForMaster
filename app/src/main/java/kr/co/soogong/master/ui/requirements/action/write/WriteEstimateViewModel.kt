package kr.co.soogong.master.ui.requirements.action.write

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.data.requirements.EstimationMessage
import kr.co.soogong.master.domain.usecase.GetEstimationUseCase
import kr.co.soogong.master.domain.usecase.SendEstimationMessageUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uiinterface.requirments.action.write.WriteEstimateActivityHelper.BUNDLE_KEY
import kr.co.soogong.master.uiinterface.requirments.action.write.WriteEstimateActivityHelper.EXTRA_STRING_KEY
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WriteEstimateViewModel @Inject constructor(
    private val sendEstimationMessageUseCase: SendEstimationMessageUseCase,
    private val getEstimationUseCase: GetEstimationUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val estimationId = savedStateHandle.get<Bundle>(BUNDLE_KEY)?.getString(EXTRA_STRING_KEY)!!

    var transmissionType: String = "통합견적"

    private val _estimation = getEstimationUseCase(estimationId)
    val estimation: LiveData<Estimation?>
        get() = _estimation

    fun sendEstimation(estimationMessage: EstimationMessage) {
        Timber.tag(TAG).d(estimationMessage.priceInNumber)

        sendEstimationMessageUseCase(
            actionType = "accept",
            keycode = estimationId,
            transmissionType = transmissionType,
            estimationMessage = estimationMessage
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            Timber.tag(TAG).d("Sending Message Succeeded: $it")
            setAction(SEND_MESSAGE_SUCCEEDED)
        }, {
            Timber.tag(TAG).d("Sending Message Failed: $it")
            setAction(SEND_MESSAGE_FAILED)
        })
    }

    companion object {
        private const val TAG = "WriteEstimateViewModel"
        const val SEND_MESSAGE_SUCCEEDED = "SIGNUP_SUCCESS"
        const val SEND_MESSAGE_FAILED = "EMAIL_ERROR"
    }
}