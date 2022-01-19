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
                } else {        // 기타 변동 사항. 선택된 값은 value 로 1을 가지고 있기 때문에, 이것만 필터한다.
                    otherOptions.find { option -> option.code == masterConfig.code && masterConfig.value == "1" }
                        ?.run { otherOption.add(this) }
                }
            }
        }
    }

    fun saveFlexibleCosts() {
        Timber.tag(TAG).d("saveFlexibleCosts: ")

        mutableListOf<MasterConfigDto>().also { list ->
            travelCost.value?.let {
                MasterConfigDto(
                    groupCode = CodeTable.FLEXIBLE_COST.code,
                    code = CodeTable.TRAVEL_COST.code,
                    name = CodeTable.TRAVEL_COST.inKorean,
                    value = it.inKorean
                ).also { travelCost ->
                    list.add(travelCost)
                }
            }

            craneUsage.value?.let {
                MasterConfigDto(
                    groupCode = CodeTable.FLEXIBLE_COST.code,
                    code = CodeTable.CRANE_USAGE.code,
                    name = CodeTable.CRANE_USAGE.inKorean,
                    value = it.inKorean
                ).also { craneUsage ->
                    list.add(craneUsage)
                }
            }

            packageCost.value?.let {
                MasterConfigDto(
                    groupCode = CodeTable.FLEXIBLE_COST.code,
                    code = CodeTable.PACKAGE_COST.code,
                    name = CodeTable.PACKAGE_COST.inKorean,
                    value = it.inKorean
                ).also { packageCost ->
                    list.add(packageCost)
                }
            }

            if (!otherCostInformation.value.isNullOrEmpty()) MasterConfigDto(
                groupCode = CodeTable.FLEXIBLE_COST.code,
                code = CodeTable.OTHER_INFO.code,
                name = CodeTable.OTHER_INFO.inKorean,
                value = otherCostInformation.value
            ).also { otherCostInformation ->
                list.add(otherCostInformation)
            }

            // 기타 변동 가능사항은 컨픽의 모든 값을 모두 데이터베이스에 저장하는데, 해당 있으면 1 없으면 0으로 저장한다.
            otherOptions.map { codeTable ->
                otherOption.value?.find { it.code == codeTable.code }.also { foundCodeTable ->
                    if (foundCodeTable != null) {
                        MasterConfigDto(
                            groupCode = CodeTable.OTHER_FLEXIBLE_OPTION.code,
                            code = foundCodeTable.code,
                            name = foundCodeTable.inKorean,
                            value = "1"
                        ).also { configDto ->
                            list.add(configDto)
                        }
                    } else {
                        MasterConfigDto(
                            groupCode = CodeTable.OTHER_FLEXIBLE_OPTION.code,
                            code = codeTable.code,
                            name = codeTable.inKorean,
                            value = "0"
                        ).also { configDto ->
                            list.add(configDto)
                        }
                    }
                }
            }

            saveMaster(
                MasterDto(
                    id = profile.value?.id,
                    uid = profile.value?.uid,
                    masterConfigDtos = list
                )
            )
        }
    }

    companion object {
        private const val TAG = "EditFlexibleCostViewModel"
    }
}