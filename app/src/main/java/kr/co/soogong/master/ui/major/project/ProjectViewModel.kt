package kr.co.soogong.master.ui.major.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.ProjectDto
import kr.co.soogong.master.domain.usecase.major.GetProjectListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val getProjectListUseCase: GetProjectListUseCase,
    val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val categoryId: MutableLiveData<Int> =
        ProjectFragment.getCategoryIdFromSavedState(savedStateHandle)

    // 뿌려주는 projects
    private val _list = MutableLiveData<List<ProjectDto>>()
    val list: LiveData<List<ProjectDto>>
        get() = _list

    // 선택된 projects
    val checkedList = ListLiveData<ProjectDto>()

    init {
        getProjectList()
    }

    private fun getProjectList() {
        Timber.tag(TAG).d("getProjectList: ")
        getProjectListUseCase(categoryId.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("getProjectList: $it")
                _list.postValue(it)
            }, {
                Timber.tag(TAG).w("getProjectList: $it")
                setAction(GET_PROJECT_FAILED)
            })
            .addToDisposable()
    }

    companion object {
        private const val TAG = "ProjectViewModel"
        const val GET_PROJECT_FAILED = "GET_PROJECT_FAILED"

    }
}