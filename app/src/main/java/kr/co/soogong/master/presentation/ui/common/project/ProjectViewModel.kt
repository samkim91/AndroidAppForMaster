package kr.co.soogong.master.presentation.ui.common.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.domain.usecase.common.major.GetProjectsUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val maxNumber: Int = ProjectFragment.getMaxNumberFromSavedState(savedStateHandle)
    private val categoryId: MutableLiveData<Int> =
        ProjectFragment.getCategoryIdFromSavedState(savedStateHandle)

    // 뿌려주는 projects
    private val _projects = MutableLiveData<List<Project>>()
    val projects: LiveData<List<Project>>
        get() = _projects

    // 선택된 projects
    val checkedList = ListLiveData<Project>()

    init {
        requestProjects()
    }

    private fun requestProjects() {
        Timber.tag(TAG).d("getProjectList: ")

        viewModelScope.launch {
            try {
                getProjectsUseCase(categoryId.value!!).let {
                    Timber.tag(TAG).d("getProjectList successfully: $it")
                    _projects.postValue(it)
                }
            } catch (e: Exception) {
                Timber.tag(TAG).w("getProjectList failed: $e")
                setAction(GET_PROJECT_FAILED)
            }
        }
    }

    companion object {
        private val TAG = ProjectViewModel::class.java.simpleName
        const val GET_PROJECT_FAILED = "GET_PROJECT_FAILED"
    }
}