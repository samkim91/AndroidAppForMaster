package kr.co.soogong.master.ui.requirements.action.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.usecase.GetEstimationUseCase
import kr.co.soogong.master.domain.usecase.RefuseToEstimateUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper.BUNDLE_KEY_ESTIMATION_KEY
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper.EXTRA_KEY_BUNDLE
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewEstimateViewModel @Inject constructor(
    getEstimationUseCase: GetEstimationUseCase,
    private val refuseToEstimateUseCase: RefuseToEstimateUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

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
                    onRefuseSuccess()
                },
                onError = {
                    Timber.tag(TAG).w("refuseToEstimate is failed: " + it)
                    onRefuseFail()
                }).addToDisposable()
    }

    private fun onRefuseSuccess(){
        setAction(SUCCESS)
    }

    private fun onRefuseFail(){
        setAction(FAIL)
    }


    companion object {
        private const val TAG = "ViewEstimateViewModel"
        const val SUCCESS = "SUCCESS"
        const val FAIL = "FAIL"
    }
}