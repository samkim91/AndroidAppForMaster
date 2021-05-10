package kr.co.soogong.master.data.profile

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

        val NULL_FLEXIBLE_COST = FlexibleCost("없어요", "엘리베이터 이용 불가시", "있어요", "test")
    }
}