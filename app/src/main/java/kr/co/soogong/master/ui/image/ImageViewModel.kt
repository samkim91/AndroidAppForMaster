package kr.co.soogong.master.ui.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel

class ImageViewModel(
    repository: Repository,
    estimationId: String
) : BaseViewModel() {
    private val _estimation = repository.getEstimation(estimationId)

    val imagePath: LiveData<List<ImagePath>>
        get() = _estimation.map {
            it?.images ?: emptyList()
        }
}