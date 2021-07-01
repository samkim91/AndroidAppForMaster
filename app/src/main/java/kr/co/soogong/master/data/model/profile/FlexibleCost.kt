package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterConfigDto

@Parcelize
data class FlexibleCost(
    val travelCostId: Int? = null,
    val craneUsageId: Int? = null,
    val packageCostId: Int? = null,
    val otherCostInformationId: Int? = null,
    val travelCost: String? = null,
    val craneUsage: String? = null,
    val packageCost: String? = null,
    val otherCostInformation: String? = null,
    val travelCostValue: String? = null,
    val craneUsageValue: String? = null,
    val packageCostValue: String? = null,
) : Parcelable {
    companion object {
        fun fromMasterConfigList(list: List<MasterConfigDto>?): FlexibleCost {
            val travelCostInDto = list?.find { masterConfigDto -> masterConfigDto.code == TravelCostCodeTable.code }
            val craneUsageInDto = list?.find { masterConfigDto -> masterConfigDto.code == CraneUsageCodeTable.code }
            val packageCostInDto = list?.find { masterConfigDto -> masterConfigDto.code == PackageCostCodeTable.code }
            val otherCostInformationInDto = list?.find { masterConfigDto -> masterConfigDto.code == OtherInfoCodeTable.code }

            return FlexibleCost(
                travelCostId = travelCostInDto?.id,
                craneUsageId = craneUsageInDto?.id,
                packageCostId = packageCostInDto?.id,
                otherCostInformationId = otherCostInformationInDto?.id,
                travelCost = travelCostInDto?.let { "${it.name} ${it.value}" },
                craneUsage = craneUsageInDto?.let { "${it.name} ${it.value}" },
                packageCost = packageCostInDto?.let { "${it.name} ${it.value}" },
                otherCostInformation = otherCostInformationInDto?.value,
                travelCostValue = travelCostInDto?.value,
                craneUsageValue = craneUsageInDto?.value,
                packageCostValue = packageCostInDto?.value,
            )
        }

        // @JvmStatic을 붙여준 이유는 fragment_profile.xml 에서 사용하기 위함. xml은 아직 java로 실행되는듯?
        @JvmStatic
        fun toList(flexibleCost: FlexibleCost?): List<String?> {
            val ret = mutableListOf<String>()
            flexibleCost?.let { fc ->
                fc.travelCost?.let { ret.add(it) }
                fc.craneUsage?.let { ret.add(it) }
                fc.packageCost?.let { ret.add(it) }
                fc.otherCostInformation?.let { ret.add(it) }
            }

            return ret
        }

    }
}