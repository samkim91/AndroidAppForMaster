package kr.co.soogong.master.ui.requirements.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import kr.co.soogong.master.util.InjectHelper
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val repository: Repository,
    private val httpClient: HttpClient
) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _progressList: LiveData<List<ProgressCard>> =
        repository.getRequirementList().map { list ->
            val ret = list.filter { it.status == "progress" }.map { ProgressCard.from(it) }

            if (ret.isNullOrEmpty()) {
                _emptyList.value = true
                updatedBadge(0)
                return@map emptyList<ProgressCard>()
            } else {
                _emptyList.value = false
                updatedBadge(ret.size)
                return@map ret.sortedByDescending { it.date }
            }
        }

    val progressList: LiveData<List<ProgressCard>>
        get() = _progressList

    fun requestList() {
        httpClient.getProgressList(InjectHelper.keyCode)
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

    private val _event = MutableLiveData<Event<Pair<String, Int>>>()
    val event: LiveData<Event<Pair<String, Int>>>
        get() = _event

    private fun updatedBadge(badgeCount: Int) {
        _event.value = Event(BADGE_UPDATE to badgeCount)
    }

    companion object {
        private const val TAG = "ProgressViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}