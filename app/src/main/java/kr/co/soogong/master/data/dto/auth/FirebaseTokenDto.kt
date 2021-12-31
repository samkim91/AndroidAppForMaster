package kr.co.soogong.master.data.dto.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseTokenDto(
    @SerializedName("personId")
    val personId: Int,

    @SerializedName("token")
    val token: String,

    ) : Parcelable {
    companion object
}