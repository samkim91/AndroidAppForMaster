package kr.co.soogong.master.data.estimation

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdditionalInfo(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("answer")
    val value: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): AdditionalInfo {
            return AdditionalInfo(
                id = jsonObject.get("id").asInt,
                description = jsonObject.get("description").asString,
                value = jsonObject.get("answer").asString
            )
        }
    }
}