package kr.co.soogong.master.ui.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel

class ImageViewModel @AssistedInject constructor(
    repository: Repository,
    @Assisted estimationId: String
) : BaseViewModel() {
    private val _estimation = repository.getEstimation(estimationId)

    val imagePath: LiveData<List<ImagePath>>
        get() = _estimation.map {
            it?.images ?: emptyList()
        }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(estimationId: String): ImageViewModel
    }

    companion object {
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