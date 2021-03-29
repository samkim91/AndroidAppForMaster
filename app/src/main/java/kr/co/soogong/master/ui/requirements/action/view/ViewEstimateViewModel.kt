package kr.co.soogong.master.ui.requirements.action.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.domain.usecase.GetEstimationUseCase
import kr.co.soogong.master.ui.base.BaseViewModel

class ViewEstimateViewModel @AssistedInject constructor(
    getEstimationUseCase: GetEstimationUseCase,
    @Assisted estimationId: String
) : BaseViewModel() {

    private val _estimation = getEstimationUseCase(estimationId)
    val estimation: LiveData<Estimation?>
        get() = _estimation

    val status: LiveData<EstimationStatus>
        get() = _estimation.map {
            EstimationStatus.getStatus(it?.status, it?.transmissions)
        }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(estimationId: String): ViewEstimateViewModel
    }

    companion object {
        private const val TAG = "ViewEstimateViewModel"

        fun provideFactory(
            assistedFactory: AssistedFactory,
            estimationId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(estimationId) as T
            }
        }
    }
}