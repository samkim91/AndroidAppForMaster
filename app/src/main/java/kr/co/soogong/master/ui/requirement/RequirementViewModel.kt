package kr.co.soogong.master.ui.requirement

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.requirement.CustomerRequest
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.common.EndlessScrollableViewModel
import kr.co.soogong.master.data.model.requirement.RequirementCardV2
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class RequirementViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : EndlessScrollableViewModel() {
    val mainTabIndex = MutableLiveData(0)
    val filterTabIndex = MutableLiveData(0)

    val masterSimpleInfo = MutableLiveData<MasterDto>()
    val requirements = ListLiveData<RequirementCardV2>()
    val customerRequests = MutableLiveData<CustomerRequest>()

    fun initList() {
        Timber.tag(TAG).d("onRefresh: ")
        requirements.clear()
        resetState()
        requestRequirements()
    }

    override fun loadMoreItems() {
        Timber.tag(TAG).d("loadMoreItems: ")
        requestRequirements()
    }

    fun requestRequirements() {
        Timber.tag(TAG).d("requestRequirements: ${mainTabIndex.value} / ${filterTabIndex.value}")

        requirementViewModelAggregate.getRequirementCardsUseCase(
            RequirementStatus.getRequirementStatusFromTabIndex(mainTabIndex.value,
                filterTabIndex.value),
            offset = offset,
            pageSize = pageSize
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirements successfully: ")
                    last = it.last
                    totalItemCount += pageSize
                    requirements.addAll(it.content)
                },
                onError = {
                    Timber.tag(TAG).d("requestRequirements failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun requestMasterSimpleInfo() {
        Timber.tag(TAG).d("requestMasterSimpleInfo: ")
        requirementViewModelAggregate.getMasterSimpleInfoUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo successful: $it")
                    masterSimpleInfo.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun getCustomerRequests() {
        Timber.tag(TAG).d("getCustomerRequests: ")

        requirementViewModelAggregate.getCustomerRequestsUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getCustomerRequests onSuccess: $it")
                    customerRequests.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("getCustomerRequests onError: ")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun callToClient(requirementId: Int) {
        Timber.tag(TAG).d("callToCustomer: $requirementId")
        requirements.value?.find { it.id == requirementId }?.estimationId?.let { estimationId ->
            requirementViewModelAggregate.callToClientUseCase(estimationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                    },
                    onError = {
                        Timber.tag(TAG).d("callToClient successfully: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun updateRequestMeasureYn(isChecked: Boolean) {
        Timber.tag(TAG)
            .d("updateRequestMeasureYn: ${masterSimpleInfo.value?.requestMeasureYn} to $isChecked")
        if (masterSimpleInfo.value?.requestMeasureYn == isChecked) return

        masterSimpleInfo.value?.uid?.let { uid ->
            requirementViewModelAggregate.updateRequestMeasureYnUseCase(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("updateRequestMeasureYn successful: $it")
                        masterSimpleInfo.postValue(it)
                    },
                    onError = {
                        Timber.tag(TAG).d("updateRequestMeasureYn failed: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun askForReview(requirementCard: RequirementCardV2?) {
        Timber.tag(TAG).d("askForReview: ")
        requirementViewModelAggregate.requestReviewUseCase(
            RepairDto(
                id = requirementCard?.repairId,
                requirementToken = requirementCard?.token,
                estimationId = requirementCard?.estimationId,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_SUCCEEDED: $it")
                    setAction(ASK_FOR_REVIEW_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun getShowNoticeForCalling() =
        requirementViewModelAggregate.getNoticeForCallingFromSharedUseCase()

    fun saveShowNoticeForCalling() {
        requirementViewModelAggregate.saveNoticeForCallingInSharedUseCase(false)
    }

    companion object {
        private const val TAG = "RequirementViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
    }
}