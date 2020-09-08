package kr.co.soogong.master.ui.requirements.received.estimate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.requirements.received.detail.DetailViewModel

class EstimateViewModelFactory(
    private val repository: Repository,
    private val receivedCardId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EstimateViewModel(repository, receivedCardId) as T
    }
}