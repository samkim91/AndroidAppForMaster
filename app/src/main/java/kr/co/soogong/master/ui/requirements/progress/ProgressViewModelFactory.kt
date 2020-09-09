package kr.co.soogong.master.ui.requirements.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.domain.Repository

class ProgressViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProgressViewModel(repository) as T
    }
}