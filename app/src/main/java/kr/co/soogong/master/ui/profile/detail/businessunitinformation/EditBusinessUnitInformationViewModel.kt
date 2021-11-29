package kr.co.soogong.master.ui.profile.detail.businessunitinformation

import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.*
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditBusinessUnitInformationViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    private val _businessTypes =
        MutableLiveData(listOf(SoleCodeTable, CorporateCodeTable, FreelancerCodeTable))
    val businessTypes: LiveData<List<CodeTable>>
        get() = _businessTypes

    val businessType = MutableLiveData<CodeTable>(_businessTypes.value?.get(0))
    val businessName = MutableLiveData("")
    val shopName = MutableLiveData("")
    val businessNumber = MutableLiveData("")
    val businessRegistImage = MutableLiveData(Uri.EMPTY)

    fun requestBusinessUnitInformation() {
        Timber.tag(TAG).d("requestBusinessUnitInformation: ")

        requestProfile {
            profile.value = it
            it.requiredInformation?.businessUnitInformation?.businessType?.let { type ->
                businessType.value =
                    _businessTypes.value?.find { mType -> mType.inKorean == type }
                it.requiredInformation.businessUnitInformation.businessName?.let { name ->
                    businessName.postValue(name)
                }
                it.requiredInformation.businessUnitInformation.shopName?.let { name ->
                    shopName.postValue(name)
                }
                it.requiredInformation.businessUnitInformation.businessRegistImage?.url?.let { image ->
                    businessRegistImage.postValue(image.toUri())
                }
                it.requiredInformation.businessUnitInformation.businessNumber?.let { number ->
                    businessNumber.postValue(number)
                }
            }
        }
    }

    fun saveBusinessUnitInformation() {
        Timber.tag(TAG).d("saveBusinessUnitInformation: ")

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                businessType = businessType.value?.code,
                businessName = if (businessType.value != FreelancerCodeTable) businessName.value else "",
                shopName = shopName.value,
                businessNumber = businessNumber.value,
                approvedStatus = if (profile.value?.approvedStatus == ApprovedCodeTable.code) RequestApproveCodeTable.code else null,
            ),
            businessRegistImageUri = businessRegistImage.value,
        )
    }

    fun clearImage(v: View) = businessRegistImage.postValue(Uri.EMPTY)

    companion object {
        private const val TAG = "EditBusinessUnitInformationViewModel"
    }
}