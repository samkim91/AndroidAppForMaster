package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.model.profile.*

@Parcelize
data class MasterConfigDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("configGroupCode")
    val groupCode: String? = null,

    @SerializedName("configGroupName")
    val groupName: String? = null,

    @SerializedName("configCode")
    val code: String? = null,

    @SerializedName("configName")
    val name: String? = null,

    @SerializedName("value")
    val value: String? = null,
) : Parcelable {
    companion object {
        fun fromFlexibleCost(flexibleCost: FlexibleCost): List<MasterConfigDto> {
            return listOf(
                MasterConfigDto(
                    id = flexibleCost.travelCostId,
                    groupCode = FlexibleCostCodeTable.code,
                    code = TravelCostCodeTable.code,
                    name = TravelCostCodeTable.inKorean,
                    value = flexibleCost.travelCostValue,
                ),
                MasterConfigDto(
                    id = flexibleCost.craneUsageId,
                    groupCode = FlexibleCostCodeTable.code,
                    code = CraneUsageCodeTable.code,
                    name = CraneUsageCodeTable.inKorean,
                    value = flexibleCost.craneUsageValue,
                ),
                MasterConfigDto(
                    id = flexibleCost.packageCostId,
                    groupCode = FlexibleCostCodeTable.code,
                    code = PackageCostCodeTable.code,
                    name = PackageCostCodeTable.inKorean,
                    value = flexibleCost.packageCostValue,
                ),
                MasterConfigDto(
                    id = flexibleCost.otherCostInformationId,
                    groupCode = FlexibleCostCodeTable.code,
                    code = OtherInfoCodeTable.code,
                    name = OtherInfoCodeTable.inKorean,
                    value = flexibleCost.otherCostInformation,
                )
            )
        }
    }
}