package kr.co.soogong.master.ui.profile.detail.requiredinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.data.model.profile.RequiredInformation
import kr.co.soogong.master.domain.usecase.profile.GetMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRequiredInformationViewModel @Inject constructor(
    private val getMasterUseCase: GetMasterUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = _profile

    val requiredInformation = MutableLiveData<RequiredInformation?>()

    fun requestRequiredInformation() {
        Timber.tag(TAG).d("requestRequiredInformation: ")

        getMasterUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { masterProfile ->
                    val profileFromMasterDto = Profile.fromMasterDto(masterProfile)
                    this._profile.value = profileFromMasterDto
                    requiredInformation.value = profileFromMasterDto.requiredInformation
                    setAction(GET_PROFILE_SUCCESSFULLY)
                    sendEvent(MASTER_APPROVED_STATUS, masterProfile.approvedStatus!!)
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
                openDate = CareerConverter.toOpenDate(careerPeriod),
                approvedStatus = RequestApproveCodeTable.code,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { requestRequiredInformation() },
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
                onSuccess = { requestRequiredInformation() },
                onError = { setAction(SAVE_MASTER_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    fun requestApprove() {
        Timber.tag(TAG).d("requestApprove: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                approvedStatus = RequestApproveCodeTable.code,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { requestRequiredInformation() },
                onError = { setAction(SAVE_MASTER_INFORMATION_FAILED) }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "EditRequiredInformationViewModel"
        const val MASTER_APPROVED_STATUS = "MASTER_APPROVED_STATUS"
        const val GET_PROFILE_SUCCESSFULLY = "GET_PROFILE_SUCCESSFULLY"
        const val GET_PROFILE_FAILED = "GET_PROFILE_FAILED"
        const val SAVE_MASTER_INFORMATION_FAILED = "SAVE_MASTER_INFORMATION_FAILED"
    }
}