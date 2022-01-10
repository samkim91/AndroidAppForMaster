package kr.co.soogong.master.ui.profile.detail.masterconfig

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditMasterConfigViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    saveMasterUseCase: SaveMasterUseCase,
) : EditProfileContainerViewModel(getProfileUseCase, saveMasterUseCase) {

    val travelCosts = listOf(CodeTable.EXIST, CodeTable.NOT_EXIST)
    val craneUsages = listOf(CodeTable.NOT_REQUIRED, CodeTable.OVER_TWO, CodeTable.NO_ELEVATOR)
    val packageCosts = listOf(CodeTable.EXIST, CodeTable.NOT_EXIST)

    val travelCost = MutableLiveData(travelCosts[0])
    val craneUsage = MutableLiveData(craneUsages[0])
    val packageCost = MutableLiveData(packageCosts[0])
    val otherCostInformation = MutableLiveData<String>()

    val otherOptions = listOf(CodeTable.MASK,
        CodeTable.OVERSHOES,
        CodeTable.DISPOSAL,
        CodeTable.ELEVATOR_PROTECTION,
        CodeTable.IN_TIME,
        CodeTable.WARRANTY,
        CodeTable.NOISE)

    val otherOption = ListLiveData<CodeTable>()

    init {
        requestFlexibleCosts()
    }

    fun requestFlexibleCosts() {
        Timber.tag(TAG).d("requestFlexibleCosts: ")

        requestProfile {
            it.basicInformation?.masterConfigs?.map { masterConfig ->
                if (masterConfig.groupCode == CodeTable.FLEXIBLE_COST.code) {       // 현장 가격 변동 요인
                    when (masterConfig.code) {
                        CodeTable.TRAVEL_COST.code -> travelCost.postValue(travelCosts.find { cost -> cost.inKorean == masterConfig.value })
                        CodeTable.CRANE_USAGE.code -> craneUsage.postValue(craneUsages.find { usage -> usage.inKorean == masterConfig.value })
                        CodeTable.PACKAGE_COST.code -> packageCost.postValue(packageCosts.find { cost -> cost.inKorean == masterConfig.value })
                        CodeTable.OTHER_INFO.code -> masterConfig.value?.run {
                            otherCostInformation.postValue(this)
                        }
                        else -> Unit
                    }
                } else {        // 기타 변동 사항
                    otherOptions.find { option -> option.code == masterConfig.code }
                        ?.run { otherOption.add(this) }
                }
            }
        }
    }

    fun saveFlexibleCosts() {
        Timber.tag(TAG).d("saveFlexibleCosts: ")

        val list = mutableListOf(
            MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.TRAVEL_COST.code,
                name = CodeTable.TRAVEL_COST.inKorean,
                value = travelCost.value?.inKorean
            ),
            MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.CRANE_USAGE.code,
                name = CodeTable.CRANE_USAGE.inKorean,
                value = craneUsage.value?.inKorean
            ),
            MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.PACKAGE_COST.code,
                name = CodeTable.PACKAGE_COST.inKorean,
                value = packageCost.value?.inKorean
            ),
            MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.OTHER_INFO.code,
                name = CodeTable.OTHER_INFO.inKorean,
                value = otherCostInformation.value ?: ""
            )
        )

        list.addAll(otherOption.value?.map { codeTable ->
            MasterConfigDto(
                groupCode = CodeTable.OTHER_FLEXIBLE_OPTION.code,
                code = codeTable.code,
                name = codeTable.inKorean,
                value = "1"
            )
        }!!)

        saveMaster(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                masterConfigDtos = list
            )
        )
    }

    companion object {
        private const val TAG = "EditFlexibleCostViewModel"
    }
}