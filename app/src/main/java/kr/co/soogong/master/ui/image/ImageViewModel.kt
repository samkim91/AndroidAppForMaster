package kr.co.soogong.master.ui.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kr.co.soogong.master.domain.usecase.requirement.GetEstimationUseCase
import kr.co.soogong.master.ui.base.BaseViewModel

class ImageViewModel @AssistedInject constructor(
    getEstimationUseCase: GetEstimationUseCase,
    @Assisted estimationId: String
) : BaseViewModel() {
    val estimation = getEstimationUseCase(estimationId)

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