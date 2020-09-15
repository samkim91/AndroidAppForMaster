package kr.co.soogong.master.ui.requirements.progress.detail.estimate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.domain.Repository

class ProgressEstimateViewModelFactory(
    private val repository: Repository,
    private val keycode: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProgressEstimateViewModel(repository, keycode) as T
    }
}