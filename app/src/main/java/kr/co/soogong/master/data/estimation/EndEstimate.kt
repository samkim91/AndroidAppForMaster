package kr.co.soogong.master.data.estimation

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.util.extension.getNullable

@Parcelize
data class EndEstimate(
    @SerializedName("actual_price")
    val actualPrice: String?,

    @SerializedName("actual_date")
    val actualDate: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): EndEstimate {
            return EndEstimate(
                actualPrice = jsonObject.get("actual_price").asString,
                actualDate = jsonObject.get("actual_date").asString
            )
        }
    }
}