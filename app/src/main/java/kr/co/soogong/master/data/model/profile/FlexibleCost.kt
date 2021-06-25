package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.ui.profile.CraneUsageCodeTable
import kr.co.soogong.master.ui.profile.OtherInfoCodeTable
import kr.co.soogong.master.ui.profile.PackageCostCodeTable
import kr.co.soogong.master.ui.profile.TravelCostCodeTable

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
                travelCost = list?.find { masterConfigDto -> masterConfigDto.code == TravelCostCodeTable.code }?.value,
                craneUsage = list?.find { masterConfigDto -> masterConfigDto.code == CraneUsageCodeTable.code }?.value,
                packageCost = list?.find { masterConfigDto -> masterConfigDto.code == PackageCostCodeTable.code }?.value,
                otherCostInformation = list?.find { masterConfigDto -> masterConfigDto.code == OtherInfoCodeTable.code }?.value,
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

        val TEST_FLEXIBLE_COST = FlexibleCost("없어요", "엘리베이터 이용 불가시", "있어요", "test")
        val NULL_FLEXIBLE_COST = FlexibleCost("", "", "", "")
    }
}