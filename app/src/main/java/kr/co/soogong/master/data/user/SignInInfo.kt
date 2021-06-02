package kr.co.soogong.master.data.user

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignInInfo(
    val phoneNumber: String,
    val userName: String,
    val token: String,
    val keycode: String,
    val isApproved: Boolean
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): SignInInfo {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return SignInInfo(
                phoneNumber = attributes.get("phoneNumber").asString,
                userName = attributes.get("userName").asString,
                token = attributes.get("token").asString,
                keycode = attributes.get("keycode").asString,
                isApproved = attributes.get("is_approved").asBoolean
            )
        }
    }
}