package kr.co.soogong.master.data.user

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignInInfo(
    val email: String,
    val username: String,
    val token: String,
    val keycode: String,
) : Parcelable {
    companion object {
        fun from(jsonObject: JsonObject): SignInInfo {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return SignInInfo(
                email = attributes.get("email").asString,
                username = attributes.get("username").asString,
                token = attributes.get("token").asString,
                keycode = attributes.get("keycode").asString
            )
        }
    }
}