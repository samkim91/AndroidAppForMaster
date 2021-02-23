package kr.co.soogong.master.data.estimation

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdditionalInfo(
    @SerializedName("id")
    val id: Int,

    @SerializedName("display_name")
    val name: String,

    @SerializedName("value")
    val value: List<String>
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): AdditionalInfo {
            val list = mutableListOf<String>()
            if (jsonObject.get("value").isJsonPrimitive) {
                list.add(jsonObject.get("value").asString)
            } else {
                for (i in jsonObject.get("value").asJsonArray) {
                    list.add(i.asString)
                }
            }

            return AdditionalInfo(
                id = jsonObject.get("id").asInt,
                name = jsonObject.get("display_name").asString,
                value = list
            )
        }
    }
}