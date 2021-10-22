package kr.co.soogong.master.ui.requirement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.requirement.CustomerRequest
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class RequirementViewModel @Inject constructor(
    private val requirementViewModelAggregate: RequirementViewModelAggregate,
) : BaseViewModel() {

    private val _masterSimpleInfo = MutableLiveData<MasterDto>()
    val masterSimpleInfo: LiveData<MasterDto>
        get() = _masterSimpleInfo

    private val _requestMeasureYn = MutableLiveData(false)
    val requestMeasureYn: LiveData<Boolean>
        get() = _requestMeasureYn

    val requirements = MutableLiveData<List<RequirementCard>>()

    val customerRequests = MutableLiveData<CustomerRequest>()

    val index = MutableLiveData(0)

    open fun requestList() {}

    fun onFilterChange(index: Int) {
        this.index.value = index
        requestList()
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
                    _masterSimpleInfo.value = it
                    it.requestMeasureYn?.let { boolean -> _requestMeasureYn.value = boolean }
                },
                onError = {
                    Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun updateRequestMeasureYn(isChecked: Boolean) {
        Timber.tag(TAG)
            .d("updateRequestMeasureYn: ${_masterSimpleInfo.value?.requestMeasureYn} to $isChecked")
        if (_masterSimpleInfo.value?.requestMeasureYn == isChecked) return

        _masterSimpleInfo.value?.uid?.let { uid ->
            requirementViewModelAggregate.updateRequestMeasureYnUseCase(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("updateRequestMeasureYn successful: $it")
                        _masterSimpleInfo.value = it
                    },
                    onError = {
                        Timber.tag(TAG).d("updateRequestMeasureYn failed: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun updateDirectRepairYn(directRepairYn: Boolean) {
        Timber.tag(TAG).d("updateDirectRepairYn to: $directRepairYn")

        requirementViewModelAggregate.updateDirectRepairYnUseCase(
            MasterDto(
                id = _masterSimpleInfo.value?.id,
                uid = _masterSimpleInfo.value?.uid,
                directRepairYn = directRepairYn)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("updateDirectRepairYn successful: $it")
                    _masterSimpleInfo.value = it
                    sendEvent(UPDATE_DIRECT_REPAIR_SUCCESSFULLY, it.directRepairYn!!)
                },
                onError = {
                    Timber.tag(TAG).d("updateDirectRepairYn failed: $it")
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
                    customerRequests.value = it
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

    companion object {
        private const val TAG = "RequirementViewModel"
        const val UPDATE_DIRECT_REPAIR_SUCCESSFULLY = "UPDATE_DIRECT_REPAIR_SUCCESSFULLY"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}