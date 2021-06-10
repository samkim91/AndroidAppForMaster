package kr.co.soogong.master.data.model.requirement

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.utility.extension.getNullable

@Parcelize
data class CancelEstimate(
    @SerializedName("message")
    val message: String,

    @SerializedName("sub_message")
    val subMessage: String?
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): CancelEstimate {
            return CancelEstimate(
                message = jsonObject.get("message").asString,
                subMessage = jsonObject.getNullable("sub_message")?.asString,
            )
        }
    }
}