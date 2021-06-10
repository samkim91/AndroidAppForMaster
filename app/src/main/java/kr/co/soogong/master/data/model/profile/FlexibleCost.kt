package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlexibleCost(
    val travelCost: String,
    val craneUsage: String,
    val packageCost: String,
    val otherCostInformation: String,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): FlexibleCost {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return FlexibleCost(
                travelCost = attributes.get("travel_cost").asString,
                craneUsage = attributes.get("crane_usage").asString,
                packageCost = attributes.get("package_cost").asString,
                otherCostInformation = attributes.get("other_cost_information").asString,
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