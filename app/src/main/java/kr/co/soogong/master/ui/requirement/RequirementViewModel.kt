package kr.co.soogong.master.ui.requirement

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import kr.co.soogong.master.data.model.requirement.RequirementCard
import kr.co.soogong.master.domain.usecase.profile.GetMasterSimpleInfoUseCase
import kr.co.soogong.master.domain.usecase.requirement.CallToClientUseCase
import kr.co.soogong.master.domain.usecase.requirement.RequestReviewUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class RequirementViewModel @Inject constructor(
    private val getMasterSimpleInfoUseCase: GetMasterSimpleInfoUseCase,
    private val callToClientUseCase: CallToClientUseCase,
    private val requestReviewUseCase: RequestReviewUseCase,
) : BaseViewModel() {

    private val _masterSimpleInfo = MutableLiveData<MasterDto>()
    val masterSimpleInfo: LiveData<MasterDto>
        get() = _masterSimpleInfo

    val requirements = MutableLiveData<List<RequirementCard>>()

    val index = MutableLiveData(0)

    open fun requestList() { }

    fun onFilterChange(index: Int) {
        this.index.value = index
        requestList()
    }

    // 문의탭의 함수들
    fun requestMasterSimpleInfo() {
        Timber.tag(TAG).d("requestMasterSimpleInfo: ")
        getMasterSimpleInfoUseCase.let {
            it().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Timber.tag(TAG).d("requestMasterSimpleInfo successful: $it")
                        _masterSimpleInfo.value = it
                    },
                    onError = {
                        Timber.tag(TAG).d("requestMasterSimpleInfo failed: $it")
                        setAction(REQUEST_FAILED)
                    }
                ).addToDisposable()
        }
    }

    // 진행탭의 함수들
    fun callToClient(requirementId: Int) {
        Timber.tag(TAG).d("callToCustomer: $requirementId")
        val requirementCard = requirements.value?.find {
            it.id == requirementId
        }

        requirementCard?.estimationDto?.id?.let { estimationId ->
            callToClientUseCase.let {
                it(estimationId).subscribeOn(Schedulers.io())
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
    }

    // 완료탭의 함수들
    fun askForReview(requirementCard: RequirementCard?) {
        Timber.tag(TAG).d("askForReview: ")
        requestReviewUseCase.let {
            it(
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
                        requestList()
                        setAction(ASK_FOR_REVIEW_SUCCESSFULLY)
                    },
                    onError = {
                        Timber.tag(TAG).d("ASK_FOR_REVIEW_FAILED: $it")
                        setAction(ASK_FOR_REVIEW_FAILED)
                    }
                ).addToDisposable()
        }
    }

    fun activateRequestingMeasurement(v: CompoundButton, isChecked: Boolean) {
        Timber.tag(TAG).d("activateRequestingMeasurement: $isChecked")
        // TODO: 2021/09/30 Re-4차 개발 예정 (마스터가 실측요청건을 받을지 안 받을지 정하는 메소드)
    }

    companion object {
        private const val TAG = "RequirementViewModel"
        const val ASK_FOR_REVIEW_SUCCESSFULLY = "ASK_FOR_REVIEW_SUCCESSFULLY"
        const val ASK_FOR_REVIEW_FAILED = "ASK_FOR_REVIEW_FAILED"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}