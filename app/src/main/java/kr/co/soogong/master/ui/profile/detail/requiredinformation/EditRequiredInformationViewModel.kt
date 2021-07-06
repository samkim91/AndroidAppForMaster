package kr.co.soogong.master.ui.profile.detail.requiredinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.model.profile.RequiredInformation
import kr.co.soogong.master.domain.usecase.auth.GetMasterApprovalUseCase
import kr.co.soogong.master.domain.usecase.profile.GetMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRequiredInformationViewModel @Inject constructor(
    val getMasterApprovalUseCase: GetMasterApprovalUseCase,
    private val getMasterUseCase: GetMasterUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _isApprovedMaster = MutableLiveData<Boolean>(getMasterApprovalUseCase())
    val isApprovedMaster: LiveData<Boolean>
        get() = _isApprovedMaster

    private val _profile = MutableLiveData<Profile>()
    val requiredInformation = MutableLiveData<RequiredInformation?>()

    fun requestRequiredInformation() {
        Timber.tag(TAG).d("requestRequiredInformation: ")

        getMasterUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { master ->
                    val profile = Profile.fromMasterDto(master)
                    _profile.value = profile
                    requiredInformation.postValue(profile.requiredInformation)
                    setAction(GET_PROFILE_SUCCESSFULLY)
                },
                onError = {
                    setAction(GET_PROFILE_FAILED)
                }
            ).addToDisposable()
    }

    fun saveCareerPeriod(careerPeriod: Int) {
        Timber.tag(TAG).d("saveCareerPeriod: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                openDate = CareerConverter.toOpenDate(careerPeriod)
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_MASTER_INFORMATION_SUCCESSFULLY) },
                onError = { setAction(SAVE_MASTER_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun saveServiceArea(radius: Int) {
        Timber.tag(TAG).d("saveServiceArea: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                serviceArea = radius,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_MASTER_INFORMATION_SUCCESSFULLY) },
                onError = { setAction(SAVE_MASTER_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun requestApprove() {
        Timber.tag(TAG).d("requestApprove: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                subscriptionPlan = "RequestApprove"
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { setAction(SAVE_MASTER_INFORMATION_SUCCESSFULLY) },
                onError = { setAction(SAVE_MASTER_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditRequiredInformationViewModel"
        const val GET_PROFILE_SUCCESSFULLY = "GET_PROFILE_SUCCESSFULLY"
        const val GET_PROFILE_FAILED = "GET_PROFILE_FAILED"
        const val SAVE_MASTER_INFORMATION_SUCCESSFULLY = "SAVE_MASTER_INFORMATION_SUCCESSFULLY"
        const val SAVE_MASTER_INFORMATION_FAILED = "SAVE_MASTER_INFORMATION_FAILED"
    }
}