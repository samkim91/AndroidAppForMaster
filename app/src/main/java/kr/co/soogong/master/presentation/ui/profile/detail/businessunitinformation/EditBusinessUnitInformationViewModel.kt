package kr.co.soogong.master.presentation.ui.profile.detail.businessunitinformation

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileViewModel
import kr.co.soogong.master.utility.ListLiveData
import javax.inject.Inject

@HiltViewModel
class EditBusinessUnitInformationViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileViewModel(getProfileUseCase, saveMasterUseCase) {

    val businessTypes = listOf(CodeTable.SOLE, CodeTable.CORPORATE, CodeTable.FREELANCER)

    val businessType = MutableLiveData(businessTypes[0])
    val businessName = MutableLiveData("")
    val shopName = MutableLiveData("")
    val businessNumber = MutableLiveData("")

    val businessRegistImage = ListLiveData<AttachmentDto>()

    init {
        requestBusinessUnitInformation()
    }

    private fun requestBusinessUnitInformation() {
        requestProfile {
            it.requiredInformation.businessUnitInformation?.businessType?.let { type ->
                businessType.value =
                    businessTypes.find { mType -> mType.inKorean == type }
                it.requiredInformation.businessUnitInformation.businessName?.let { name ->
                    businessName.postValue(name)
                }
                it.requiredInformation.businessUnitInformation.shopName?.let { name ->
                    shopName.postValue(name)
                }
                it.requiredInformation.businessUnitInformation.businessRegistImage?.let { image ->
                    businessRegistImage.add(image)
                }
                it.requiredInformation.businessUnitInformation.businessNumber?.let { number ->
                    businessNumber.postValue(number)
                }
            }
        }
    }

    fun saveBusinessUnitInformation() {
        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                businessType = businessType.value?.code,
                businessName = if (businessType.value != CodeTable.FREELANCER) businessName.value else "",
                shopName = shopName.value,
                businessNumber = businessNumber.value,
                approvedStatus = if (profile.value?.approvedStatus == CodeTable.APPROVED.code) CodeTable.REQUEST_APPROVE.code else null,
            ),
            businessRegistImageUri = businessRegistImage.value?.first()?.uri,
        )
    }

    companion object {
        private const val TAG = "EditBusinessUnitInformationViewModel"
    }
}