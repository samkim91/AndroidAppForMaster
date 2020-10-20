package kr.co.soogong.master.ui.select.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class ProjectSelectViewModel(
    private val category: Category
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
        HttpClient.getProjectList(category)
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

    companion object {
        private const val TAG = "ProjectSelectViewModel"
    }
}