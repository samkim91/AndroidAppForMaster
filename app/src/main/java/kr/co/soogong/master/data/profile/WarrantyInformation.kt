package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class WarrantyInformation(
    val warrantyPeriod: String,
    val warrantyDescription: String,
) : Parcelable{
    companion object {
        fun fromJson(jsonObject: JsonObject): WarrantyInformation {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return WarrantyInformation(
                warrantyPeriod = attributes.get("warrantyPeriod").asString,
                warrantyDescription = attributes.get("warrantyDescription").asString,
            )
        }

        val TEST_WARRANTY_INFORMATION = WarrantyInformation("2년", "A/S 잘해요A/S 잘해요A/S 잘해요A/S 잘해요A/S 잘해요A/S 잘해요A/S 잘해요")
        val NULL_WARRANTY_INFORMATION = WarrantyInformation("", "")
    }
}