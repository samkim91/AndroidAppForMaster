package kr.co.soogong.master.ui.major.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.data.model.major.Project
import kr.co.soogong.master.domain.usecase.major.GetProjectListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber

class ProjectViewModel @AssistedInject constructor(
    private val getProjectListUseCase: GetProjectListUseCase,
    @Assisted private val category: Category,
) : BaseViewModel() {
    private val _list = MutableLiveData<List<Project>>()
    val list: LiveData<List<Project>>
        get() = _list

    init {
        getProjectList()
    }

//    fun getCheckedList() = list.value?.filter { it.checked }

    fun changeList(position: Int, project: Project) {
        val items = list.value?.toMutableList()
        items?.set(position, project)
        _list.postValue(items ?: emptyList())
    }

    private fun getProjectList() {
        Timber.tag(TAG).d("getProjectList: ")
        getProjectListUseCase(category.id)
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

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(category: Category): ProjectViewModel
    }

    companion object {
        private const val TAG = "ProjectViewModel"
        const val GET_PROJECT_FAILED = "GET_PROJECT_FAILED"

        fun provideFactory(
            assistedFactory: AssistedFactory,
            category: Category
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(category) as T
            }
        }
    }
}