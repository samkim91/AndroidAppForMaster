package kr.co.soogong.master.data.model.requirement

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    @SerializedName("contents")
    val contents: String,

    @SerializedName("price_detail")
    val priceDetail: List<PriceDetail>,

    @SerializedName("price_in_number")
    val priceInNumber: Int,

    @SerializedName("status")
    val status: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject?): Message? {
            jsonObject ?: return null

            val list = mutableListOf<PriceDetail>()
            for (item in jsonObject.get("price_detail").asJsonArray) {
                list.add(PriceDetail.fromJson(item.asJsonObject))
            }
            return Message(
                contents = jsonObject.get("contents").asString,
                priceDetail = list,
                priceInNumber = jsonObject.get("price_in_number").asInt,
                status = jsonObject.get("status").asString,
            )
        }
    }
}