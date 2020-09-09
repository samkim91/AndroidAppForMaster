package kr.co.soogong.master.ui.requirements.progress.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber

class ProgressDetailViewModel(
    repository: Repository,
    id: Long
) : BaseViewModel() {
    private lateinit var keycode: String

    private val _requirement = repository.getRequirement(id).map {
        Timber.tag(TAG).d(": $it")
        it?.let {
            keycode = it.keycode
        }
        it
    }

    val requirement: LiveData<Requirement?>
        get() = _requirement

    companion object {
        private const val TAG = "ProgressDetailViewModel"
    }
}