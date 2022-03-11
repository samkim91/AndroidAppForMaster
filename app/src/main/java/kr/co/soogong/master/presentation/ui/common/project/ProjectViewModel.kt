package kr.co.soogong.master.presentation.ui.common.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
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
        getProjectsUseCase(categoryId.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getProjectList successfully: $it")
                    _projects.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).w("getProjectList failed: $it")
                    setAction(GET_PROJECT_FAILED)
                })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "ProjectViewModel"
        const val GET_PROJECT_FAILED = "GET_PROJECT_FAILED"
    }
}