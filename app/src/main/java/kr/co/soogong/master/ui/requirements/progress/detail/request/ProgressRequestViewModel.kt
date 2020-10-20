package kr.co.soogong.master.ui.requirements.progress.detail.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import java.util.*

class ProgressRequestViewModel(
    repository: Repository,
    keycode: String
) : BaseViewModel() {
    private val _requirement = repository.getRequirement(keycode)
    val requirement: LiveData<Requirement?>
        get() = _requirement

    val userName: LiveData<String?>
        get() = _requirement.map {
            it?.userName
        }

    val category: LiveData<String?>
        get() = _requirement.map {
            it?.category
        }

    val location: LiveData<String>
        get() = _requirement.map {
            it?.location ?: ""
        }

    val date: LiveData<Date?>
        get() = _requirement.map {
            it?.date
        }

    val content: LiveData<String?>
        get() = _requirement.map {
            it?.content
        }

    val houseType: LiveData<String?>
        get() = _requirement.map {
            it?.houseType
        }

    val size: LiveData<String?>
        get() = _requirement.map {
            it?.size
        }

    val image: LiveData<String?>
        get() = _requirement.map {
            it?.image
        }

    val status: LiveData<String?>
        get() = _requirement.map {
            it?.status
        }

    companion object {
        private const val TAG = "ProgressRequestViewMode"
    }
}