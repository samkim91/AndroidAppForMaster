package kr.co.soogong.master.ui.requirements.done

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.requirements.progress.ProgressCard
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class DoneViewModel(
    private val repository: Repository
) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _doneList: LiveData<List<ProgressCard>> =
        repository.getRequirementList().map { list ->
            val ret = list.filter { it.status == "done" }.map { ProgressCard.from(it) }

            if (ret.isNullOrEmpty()) {
                _emptyList.value = true
                return@map emptyList<ProgressCard>()
            } else {
                _emptyList.value = false
                return@map ret.sortedByDescending { it.date }
            }
        }

    fun requestList() {
        HttpClient.getProgressList(InjectHelper.keyCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Timber.tag(TAG).d("requestList: $it")
                viewModelScope.launch {
                    repository.insertRequirement(it)
                }
            }
            .addToDisposable()
    }

    val doneList: LiveData<List<ProgressCard>>
        get() = _doneList

    companion object {
        private const val TAG = "DoneViewModel"
    }
}