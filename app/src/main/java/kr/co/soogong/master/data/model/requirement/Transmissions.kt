package kr.co.soogong.master.data.model.requirement

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.utility.extension.getNullable

@Parcelize
data class Transmissions(
    @SerializedName("branch_keycode")
    val branchKeycode: String,

    @SerializedName("message")
    val message: Message?,

    @SerializedName("status")
    val status: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Transmissions {
            return Transmissions(
                branchKeycode = jsonObject.get("branch_keycode").asString,
                message = Message.fromJson(jsonObject.getNullable("message")?.asJsonObject),
                status = jsonObject.get("status").asString
            )
        }
    }
}