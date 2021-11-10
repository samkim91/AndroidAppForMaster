package kr.co.soogong.master.ui.requirement

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.requirement.CustomerRequest
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.data.model.requirement.RequirementStatus
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class RequirementViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : BaseViewModel() {
    val mainTabIndex = MutableLiveData(0)
    val filterTabIndex = MutableLiveData(0)

    val masterSimpleInfo = MutableLiveData<MasterDto>()
    val requirements = MutableLiveData<List<RequirementCard>>()
    val customerRequests = MutableLiveData<CustomerRequest>()

    fun requestRequirements() {
        Timber.tag(TAG).d("requestRequirements: ${mainTabIndex.value} / ${filterTabIndex.value}")

        requirementViewModelAggregate.getRequirementCardsUseCase(
            RequirementStatus.getRequirementStatusFromTabIndex(mainTabIndex.value,
                filterTabIndex.value)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestRequirements successfully: ")
                    requirements.postValue(it)
                },
                onError = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    // region : 문의현황 프래그먼트 로드 시 실행 함수
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
    // end region : 문의현황 프래그먼트 로드 시 실행 함수

    // region : 문의, 진행탭에서 실행할 수 있는 함수
    fun callToClient(requirementId: Int) {
        Timber.tag(TAG).d("callToCustomer: $requirementId")
        requirements.value?.find { it.id == requirementId }?.estimationDto?.id?.let { estimationId ->
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
    // end region : 문의, 진행탭에서 실행할 수 있는 함수

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

    fun askForReview(requirementCard: RequirementCard?) {
        Timber.tag(TAG).d("askForReview: ")
        requirementViewModelAggregate.requestReviewUseCase(
            RepairDto(
                id = requirementCard?.estimationDto?.repair?.id,
                requirementToken = requirementCard?.token,
                estimationId = requirementCard?.estimationDto?.id,
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
                    setAction(ASK_FOR_REVIEW_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "RequirementViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val ASK_FOR_REVIEW_FAILED = "ASK_FOR_REVIEW_FAILED"
    }
}