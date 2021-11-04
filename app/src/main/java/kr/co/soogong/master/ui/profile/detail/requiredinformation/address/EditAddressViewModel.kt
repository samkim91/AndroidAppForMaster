package kr.co.soogong.master.ui.profile.detail.requiredinformation.address

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditAddressViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val roadAddress = MutableLiveData("")
    val detailAddress = MutableLiveData("")
    val latitude = MutableLiveData(0.0)
    val longitude = MutableLiveData(0.0)

    fun requestCompanyAddress() {
        Timber.tag(TAG).d("requestCompanyAddress: ")

        requestProfile {
            profile.value = it
            roadAddress.postValue(it.requiredInformation?.companyAddress?.roadAddress)
            detailAddress.postValue(it.requiredInformation?.companyAddress?.detailAddress)
        }
    }

    fun saveCompanyAddress() {
        Timber.tag(TAG).d("saveCompanyAddress: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                roadAddress = roadAddress.value,
                detailAddress = detailAddress.value,
                latitude = latitude.value?.toFloat(),
                longitude = longitude.value?.toFloat(),
            )
        )
    }

    companion object {
        private const val TAG = "EditAddressViewModel"
    }
}