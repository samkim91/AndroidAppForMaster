package kr.co.soogong.master.ui.requirements.progress.detail.estimate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel

class ProgressEstimateViewModel @AssistedInject constructor(
    repository: Repository,
    @Assisted keycode: String
) : BaseViewModel() {

    private val _requirement = repository.getRequirement(keycode)

    val requirement: LiveData<Requirement?>
        get() = _requirement

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(keycode: String): ProgressEstimateViewModel
    }

    companion object {
        private const val TAG = "CardDetailViewModel"

        fun provideFactory(
            assistedFactory: AssistedFactory,
            keycode: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(keycode) as T
            }
        }
    }
}