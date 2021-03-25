package kr.co.soogong.master.ui.requirements.received

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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    private val estimationDao: EstimationDao,
    private val estimationsService: EstimationsService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
) : BaseViewModel() {
    private val _emptyList = MutableLiveData(true)
    val emptyList: LiveData<Boolean>
        get() = _emptyList

    private val _requirementList: LiveData<List<RequirementCard>> =
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
    val requirementList: LiveData<List<RequirementCard>>
        get() = _requirementList

    private val _event = MutableLiveData<Event<Pair<String, Int>>>()
    val event: LiveData<Event<Pair<String, Int>>>
        get() = _event

    private fun updatedBadge(badgeCount: Int) {
        Timber.tag(TAG).d("updatedBadge: $badgeCount")
        _event.value = Event(BADGE_UPDATE to badgeCount)
    }

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

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
    }
}