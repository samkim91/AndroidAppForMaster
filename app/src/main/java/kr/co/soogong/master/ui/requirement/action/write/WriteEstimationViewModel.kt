package kr.co.soogong.master.ui.requirement.action.write

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.EstimationMessage
import kr.co.soogong.master.domain.usecase.requirement.GetRequirementUseCase
import kr.co.soogong.master.domain.usecase.requirement.SendEstimationMessageUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uihelper.requirment.action.write.WriteEstimateActivityHelper.BUNDLE_KEY
import kr.co.soogong.master.uihelper.requirment.action.write.WriteEstimateActivityHelper.EXTRA_STRING_KEY
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WriteEstimationViewModel @Inject constructor(
    private val sendEstimationMessageUseCase: SendEstimationMessageUseCase,
    private val getRequirementUseCase: GetRequirementUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val estimationId = savedStateHandle.get<Bundle>(BUNDLE_KEY)?.getInt(EXTRA_STRING_KEY)!!

    var estimationType: String = "통합견적"

    private val _requirement = MutableLiveData<RequirementDto>()
    val requirement: LiveData<RequirementDto>
        get() = _requirement

    init {

    }

    fun requestRequirement() {
        getRequirementUseCase(estimationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _requirement.value = it
                },
                onError = {
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun sendEstimation(estimationMessage: EstimationMessage) {
//        sendEstimationMessageUseCase(
//            keycode = estimationId,
//            transmissionType = transmissionType,
//            estimationMessage = estimationMessage
//        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
//            Timber.tag(TAG).d("Sending Message Succeeded: $it")
//            setAction(SEND_MESSAGE_SUCCESSFULLY)
//        }, {
//            Timber.tag(TAG).d("Sending Message Failed: $it")
//            setAction(SEND_MESSAGE_FAILED)
//        }).addToDisposable()
    }

    companion object {
        private const val TAG = "WriteEstimationViewModel"

        const val SEND_MESSAGE_SUCCESSFULLY = "SEND_MESSAGE_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val SEND_MESSAGE_FAILED = "EMAIL_ERROR"
    }
}