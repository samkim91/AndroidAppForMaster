package kr.co.soogong.master.ui.requirements.progress.detail

import androidx.lifecycle.LiveData
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel

class ProgressDetailViewModel(
    repository: Repository,
    keycode: String
) : BaseViewModel() {

    private val _requirement = repository.getRequirement(keycode)

    val requirement: LiveData<Requirement?>
        get() = _requirement

    companion object {
        private const val TAG = "ProgressDetailViewModel"
    }
}