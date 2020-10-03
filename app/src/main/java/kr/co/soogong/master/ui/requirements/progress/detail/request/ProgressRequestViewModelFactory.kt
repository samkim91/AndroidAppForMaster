package kr.co.soogong.master.ui.requirements.progress.detail.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.domain.Repository

class ProgressRequestViewModelFactory(
    private val repository: Repository,
    private val keycode: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProgressRequestViewModel(repository, keycode) as T
    }
}