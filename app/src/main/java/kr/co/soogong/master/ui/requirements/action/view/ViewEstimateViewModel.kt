package kr.co.soogong.master.ui.requirements.action.view

import android.os.Bundle
import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.usecase.GetEstimationUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper
import javax.inject.Inject

@HiltViewModel
class ViewEstimateViewModel @Inject constructor(
    private val getEstimationUseCase: GetEstimationUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val estimationId = savedStateHandle.get<Bundle>(ViewEstimateActivityHelper.EXTRA_KEY_BUNDLE)?.getString(ViewEstimateActivityHelper.BUNDLE_KEY_ESTIMATION_KEY)!!

    private val _estimation = getEstimationUseCase(estimationId)
    val estimation: LiveData<Estimation?>
        get() = _estimation

    val status: LiveData<EstimationStatus>
        get() = _estimation.map {
            EstimationStatus.getStatus(it?.status, it?.transmissions)
        }

    companion object {
        private const val TAG = "ViewEstimateViewModel"

    }
}