package kr.co.soogong.master.ui.requirements.received.estimate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.domain.Repository

class EstimateViewModelFactory(
    private val repository: Repository,
    private val keycode: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EstimateViewModel(repository, keycode) as T
    }
}