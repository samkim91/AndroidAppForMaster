package kr.co.soogong.master.presentation.ui.profile.detail.warranty

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.DropdownItemList
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditWarrantyInformationViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileViewModel(getProfileUseCase, saveMasterUseCase) {

    val warrantyPeriods = DropdownItemList.warrantyPeriods
    val selectedPeriod = MutableLiveData<Pair<String, Int>>()
    val warrantyDescription = MutableLiveData("")

    init {
        requestWarrantyInformation()
    }

    private fun requestWarrantyInformation() {
        requestProfile {
            it.requiredInformation.warrantyInformation?.warrantyPeriod?.let { period ->
                Timber.tag(TAG).d("warrantyPeriod: $period")

                selectedPeriod.postValue(warrantyPeriods.find { pair ->
                    pair.second == period
                })
            }
            it.requiredInformation.warrantyInformation?.warrantyDescription?.let { description ->
                warrantyDescription.postValue(description)
            }
        }
    }

    fun saveWarrantyInfo() {
        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                warrantyPeriod = selectedPeriod.value?.second,
                warrantyDescription = if (selectedPeriod.value?.second != -1) warrantyDescription.value else null,
            )
        )
    }

    companion object {
        private const val TAG = "EditWarrantyInformationViewModel"
    }
}