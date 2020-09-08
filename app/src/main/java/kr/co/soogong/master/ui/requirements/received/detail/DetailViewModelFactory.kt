package kr.co.soogong.master.ui.requirements.received.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.domain.Repository

class DetailViewModelFactory(
    private val repository: Repository,
    private val receivedCardId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(repository, receivedCardId) as T
    }
}