package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterConfigDto

@Parcelize
data class FlexibleCost(
    val travelCost: String?,
    val craneUsage: String?,
    val packageCost: String?,
    val otherCostInformation: String?,
) : Parcelable {
    companion object {
        fun fromMasterConfigList(list: List<MasterConfigDto>?): FlexibleCost {
            return FlexibleCost(
                travelCost = list?.find { masterConfigDto -> masterConfigDto.code == "TravelCost" }?.value ?: "",
                craneUsage = list?.find { masterConfigDto -> masterConfigDto.code == "CraneUsage" }?.value ?: "",
                packageCost = list?.find { masterConfigDto -> masterConfigDto.code == "PackageCost" }?.value ?: "",
                otherCostInformation = list?.find { masterConfigDto -> masterConfigDto.code == "OtherInfo" }?.value ?: "",
            )
        }

        // @JvmStatic을 붙여준 이유는 fragment_profile.xml 에서 사용하기 위함. xml은 아직 java로 실행되는듯?
        @JvmStatic fun toList(flexibleCost: FlexibleCost?): List<String?> {
            return if(flexibleCost == null) {
                emptyList()
            } else {
                listOf(flexibleCost.travelCost, flexibleCost.craneUsage, flexibleCost.packageCost)
            }
        }

        val TEST_FLEXIBLE_COST = FlexibleCost("없어요", "엘리베이터 이용 불가시", "있어요", "test")
        val NULL_FLEXIBLE_COST = FlexibleCost("", "", "", "")
    }
}