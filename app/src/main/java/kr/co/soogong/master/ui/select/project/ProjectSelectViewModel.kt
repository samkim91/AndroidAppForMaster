package kr.co.soogong.master.ui.select.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.domain.usecase.GetProjectListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber

class ProjectSelectViewModel @AssistedInject constructor(
    private val getProjectListUseCase: GetProjectListUseCase,
    @Assisted private val category: Category
) : BaseViewModel() {
    private val _list = MutableLiveData<List<Project>>()
    val list: LiveData<List<Project>>
        get() = _list

    init {
        getProjectList()
    }

    fun getCheckedList() = list.value?.filter { it.checked }

    fun changeList(position: Int, project: Project) {
        val items = list.value?.toMutableList()
        items?.set(position, project)
        _list.postValue(items ?: emptyList())
    }

    private fun getProjectList() {
        getProjectListUseCase(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag(TAG).d("getProjectList: $it")
                _list.postValue(it)
            }, {
                Timber.tag(TAG).w("getProjectList: $it")
            })
            .addToDisposable()
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(category: Category): ProjectSelectViewModel
    }

    companion object {
        private const val TAG = "ProjectSelectViewModel"

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