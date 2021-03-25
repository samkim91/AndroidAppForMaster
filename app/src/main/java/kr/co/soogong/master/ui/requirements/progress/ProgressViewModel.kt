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
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.requirements.RequirementCard
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.EstimationsService
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.util.Event
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val estimationDao: EstimationDao,
    private val estimationsService: EstimationsService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _progressList: LiveData<List<RequirementCard>> =
        estimationDao.getAllList().map { list ->
            val ret = list.map { RequirementCard.from(it) }

            if (ret.isNullOrEmpty()) {
                _emptyList.value = true
                updatedBadge(0)
                return@map emptyList<RequirementCard>()
            } else {
                _emptyList.value = false
                updatedBadge(ret.size)
                return@map ret
            }
        }

    val progressList: LiveData<List<RequirementCard>>
        get() = _progressList

    fun requestList() {
        estimationsService.getEstimationList(getMasterKeyCodeUseCase())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                viewModelScope.launch {
                    estimationDao.insert(it)
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