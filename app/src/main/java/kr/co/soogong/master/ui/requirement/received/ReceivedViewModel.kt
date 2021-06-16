package kr.co.soogong.master.ui.requirement.received

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.requirement.GetReceivedRequirementListUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    val getMasterApprovalUseCase: GetMasterApprovalUseCase,
    private val getReceivedRequirementListUseCase: GetReceivedRequirementListUseCase
) : BaseViewModel() {
    private val _isApprovedMaster = MutableLiveData<Boolean>(getMasterApprovalUseCase())
    val isApprovedMaster: LiveData<Boolean>
        get() = _isApprovedMaster

    val requirementList = ListLiveData<RequirementCard>()

    fun requestList() {
        Timber.tag(TAG).d("requestList: ")
        getReceivedRequirementListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    requirementList.addAll(it)
                    sendEvent(BADGE_UPDATE, requirementList.getItemCount())
                },
                onError = {
                    setAction(REQUEST_LIST_FAILED)
                    requirementList.clear()
                }
            ).addToDisposable()
    }

    fun onFilterChange(index: Int) {
        Timber.tag(TAG).d("onFilterChange: $index")

        getReceivedRequirementListUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { list ->
                    requirementList.clear()
                    when (index) {
                        1 -> {
                            requirementList.addAll(list.filter { it.status == RequirementStatus.Requested.toString() })
                        }
                        2 -> {
                            requirementList.addAll(list.filter { it.status == RequirementStatus.Estimated.toString() })
                        }
                        else -> {
                            requirementList.addAll(list)
                        }
                    }
                },
                onError = {
                    setAction(REQUEST_LIST_FAILED)
                    requirementList.clear()
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ReceivedViewModel"
        const val BADGE_UPDATE = "BADGE_UPDATE"
        const val REQUEST_LIST_FAILED = "REQUEST_LIST_FAILED"
    }
}