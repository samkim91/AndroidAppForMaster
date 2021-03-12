package kr.co.soogong.master.ui.requirements.action.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kr.co.soogong.master.data.estimation.AdditionalInfo
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.data.estimation.Transmissions
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.requirements.card.EstimationStatus
import java.util.*

class ViewEstimateViewModel(
    private val repository: Repository,
    private val estimationId: String
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
}