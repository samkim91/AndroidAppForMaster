package kr.co.soogong.master.ui.profile.detail.flexiblecost

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFlexibleCostViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val flexibleCost = MutableLiveData<List<MasterConfigDto>>()

    val travelCostValue = MutableLiveData<String>()
    val craneUsageValue = MutableLiveData<String>()
    val packageCostValue = MutableLiveData<String>()
    val otherCostInformation = MutableLiveData<String>()

    fun requestFlexibleCosts() {
        Timber.tag(TAG).d("requestFlexibleCosts: ")

        requestProfile {
            profile.postValue(it)
            it.basicInformation?.flexibleCost?.let { masterConfigList ->
                flexibleCost.postValue(masterConfigList)

                masterConfigList.find { masterConfigDto -> masterConfigDto.code == CodeTable.TRAVEL_COST.code }?.value?.let { value ->
                    travelCostValue.postValue(value)
                }
                masterConfigList.find { masterConfigDto -> masterConfigDto.code == CodeTable.CRANE_USAGE.code }?.value?.let { value ->
                    craneUsageValue.postValue(value)
                }
                masterConfigList.find { masterConfigDto -> masterConfigDto.code == CodeTable.PACKAGE_COST.code }?.value?.let { value ->
                    packageCostValue.postValue(value)
                }
                masterConfigList.find { masterConfigDto -> masterConfigDto.code == CodeTable.OTHER_INFO.code }?.value?.let { value ->
                    otherCostInformation.postValue(value)
                }
            }
        }
    }

    fun saveFlexibleCosts() {
        Timber.tag(TAG).d("saveFlexibleCosts: ")

        val configList = listOf(
            MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.TRAVEL_COST.code,
                name = CodeTable.TRAVEL_COST.inKorean,
                value = travelCostValue.value
            ),
            MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.CRANE_USAGE.code,
                name = CodeTable.CRANE_USAGE.inKorean,
                value = craneUsageValue.value
            ),
            MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.PACKAGE_COST.code,
                name = CodeTable.PACKAGE_COST.inKorean,
                value = packageCostValue.value
            ),
            MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.OTHER_INFO.code,
                name = CodeTable.OTHER_INFO.inKorean,
                value = if (!otherCostInformation.value.isNullOrEmpty()) otherCostInformation.value else null
            ),
        )

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                masterConfigs = configList
            )
        )
    }

    companion object {
        private const val TAG = "EditFlexibleCostViewModel"
    }
}