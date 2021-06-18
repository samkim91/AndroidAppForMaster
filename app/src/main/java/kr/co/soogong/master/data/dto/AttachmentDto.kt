package kr.co.soogong.master.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttachmentDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("url")
    val url: String?,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,
) : Parcelable {
    companion object {

    }
}