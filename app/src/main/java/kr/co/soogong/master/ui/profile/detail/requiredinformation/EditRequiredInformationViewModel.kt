package kr.co.soogong.master.ui.profile.detail.requiredinformation

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.data.model.profile.RequiredInformation
import kr.co.soogong.master.domain.usecase.profile.GetMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.CareerConverter
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRequiredInformationViewModel @Inject constructor(
    private val getMasterUseCase: GetMasterUseCase,
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val requiredInformation = MutableLiveData<RequiredInformation?>()
    val percentage = MutableLiveData<Float>()

    fun requestRequiredInformation() {
        Timber.tag(TAG).d("requestRequiredInformation: ")

        getMasterUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { master ->
                    Profile.fromMasterDto(master).let {
                        profile.value = it
                        requiredInformation.value = it.requiredInformation
                    }
                    setAction(GET_PROFILE_SUCCESSFULLY)
                    sendEvent(MASTER_APPROVED_STATUS, master.approvedStatus!!)
                },
                onError = {
                    setAction(GET_PROFILE_FAILED)
                }
            ).addToDisposable()
    }

    fun saveCareerPeriod(careerPeriod: Int) {
        Timber.tag(TAG).d("saveCareerPeriod: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                openDate = CareerConverter.toOpenDate(careerPeriod),
                approvedStatus = if (profile.value?.approvedStatus == ApprovedCodeTable.code) RequestApproveCodeTable.code else null,
            )
        )
    }

    fun saveServiceArea(radius: Int) {
        Timber.tag(TAG).d("saveServiceArea: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                serviceArea = radius,
            )
        )
    }

    fun requestApprove() {
        Timber.tag(TAG).d("requestApprove: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                approvedStatus = RequestApproveCodeTable.code,
            )
        )
    }

    companion object {
        private const val TAG = "EditRequiredInformationViewModel"
        const val MASTER_APPROVED_STATUS = "MASTER_APPROVED_STATUS"
        const val GET_PROFILE_SUCCESSFULLY = "GET_PROFILE_SUCCESSFULLY"
        const val GET_PROFILE_FAILED = "GET_PROFILE_FAILED"
        const val SAVE_MASTER_INFORMATION_FAILED = "SAVE_MASTER_INFORMATION_FAILED"
    }
}