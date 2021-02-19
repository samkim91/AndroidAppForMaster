package kr.co.soogong.master.ui.requirements.done

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.domain.Repository

class DoneViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DoneViewModel(repository) as T
    }
}