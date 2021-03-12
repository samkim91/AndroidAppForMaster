package kr.co.soogong.master.data.estimation

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceDetail(
    @SerializedName("price_in_number")
    val priceInNumber: Int,

    @SerializedName("price_type")
    val priceType: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): PriceDetail {
            return PriceDetail(
                priceInNumber = jsonObject.get("price_in_number").asInt,
                priceType = jsonObject.get("price_type").asString
            )
        }
    }
}