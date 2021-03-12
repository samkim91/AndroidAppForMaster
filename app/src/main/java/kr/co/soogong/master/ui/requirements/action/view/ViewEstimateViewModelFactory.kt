package kr.co.soogong.master.ui.requirements.action.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.domain.Repository

class ViewEstimateViewModelFactory(
    private val repository: Repository,
    private val estimationId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewEstimateViewModel(repository, estimationId) as T
    }
}