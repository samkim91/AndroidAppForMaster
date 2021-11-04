package kr.co.soogong.master.ui.profile.detail.flexiblecost

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.*
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
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

                masterConfigList.find { masterConfigDto -> masterConfigDto.code == TravelCostCodeTable.code }?.value?.let { value ->
                    travelCostValue.postValue(value)
                }
                masterConfigList.find { masterConfigDto -> masterConfigDto.code == CraneUsageCodeTable.code }?.value?.let { value ->
                    craneUsageValue.postValue(value)
                }
                masterConfigList.find { masterConfigDto -> masterConfigDto.code == PackageCostCodeTable.code }?.value?.let { value ->
                    packageCostValue.postValue(value)
                }
                masterConfigList.find { masterConfigDto -> masterConfigDto.code == OtherInfoCodeTable.code }?.value?.let { value ->
                    otherCostInformation.postValue(value)
                }
            }
        }
    }

    fun saveFlexibleCosts() {
        Timber.tag(TAG).d("saveFlexibleCosts: ")

        val configList = listOf(
            MasterConfigDto(
                groupCode = FlexibleCostCodeTable.code,
                code = TravelCostCodeTable.code,
                name = TravelCostCodeTable.inKorean,
                value = travelCostValue.value
            ),
            MasterConfigDto(
                groupCode = FlexibleCostCodeTable.code,
                code = CraneUsageCodeTable.code,
                name = CraneUsageCodeTable.inKorean,
                value = craneUsageValue.value
            ),
            MasterConfigDto(
                groupCode = FlexibleCostCodeTable.code,
                code = PackageCostCodeTable.code,
                name = PackageCostCodeTable.inKorean,
                value = packageCostValue.value
            ),
            MasterConfigDto(
                groupCode = FlexibleCostCodeTable.code,
                code = OtherInfoCodeTable.code,
                name = OtherInfoCodeTable.inKorean,
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