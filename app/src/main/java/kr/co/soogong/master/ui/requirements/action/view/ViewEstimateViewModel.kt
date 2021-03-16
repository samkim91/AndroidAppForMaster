package kr.co.soogong.master.ui.requirements.action.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kr.co.soogong.master.data.estimation.AdditionalInfo
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.data.estimation.Transmissions
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.ui.base.BaseViewModel
import java.util.*

class ViewEstimateViewModel @AssistedInject constructor(
    repository: Repository,
    @Assisted estimationId: String
) : BaseViewModel() {

    private val _estimation = repository.getEstimation(estimationId)

    val estimation: LiveData<Estimation?>
        get() = _estimation

    val id: LiveData<String?>
        get() = _estimation.map {
            it?.keycode
        }

    val status: LiveData<EstimationStatus>
        get() = _estimation.map {
            EstimationStatus.getStatus(it?.status, it?.transmissions)
        }

    val address: LiveData<String?>
        get() = _estimation.map {
            it?.address
        }

    val project: LiveData<String?>
        get() = _estimation.map {
            it?.project
        }

    val createdAt: LiveData<Date>
        get() = _estimation.map {
            Date(it?.createdAt ?: System.currentTimeMillis())
        }

    val additionInfo: LiveData<List<AdditionalInfo>?>
        get() = _estimation.map {
            it?.additionalInfo
        }

    val imageList: LiveData<List<ImagePath>?>
        get() = _estimation.map {
            it?.images
        }

    val transmissions: LiveData<Transmissions?>
        get() = _estimation.map {
            it?.transmissions
        }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(estimationId: String): ViewEstimateViewModel
    }

    companion object {
        private const val TAG = "ViewEstimateViewModel"

        fun provideFactory(
            assistedFactory: ViewEstimateViewModel.AssistedFactory,
            estimationId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(estimationId) as T
            }
        }
    }
}