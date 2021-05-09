package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlexibleCost(
    val travelCost: Int,    // 0: false, 1: true, 2: third option
    val craneUsage: Int,
    val packageCost: Int,
    val otherCostInformation: String,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): FlexibleCost {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return FlexibleCost(
                travelCost = attributes.get("travel_cost").asInt,
                craneUsage = attributes.get("crane_usage").asInt,
                packageCost = attributes.get("package_cost").asInt,
                otherCostInformation = attributes.get("other_cost_information").asString,
            )
        }

        val NULL_FLEXIBLE_COST = FlexibleCost(0, 0, 0, "test")
    }
}