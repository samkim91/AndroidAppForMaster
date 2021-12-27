package kr.co.soogong.master.data.dto.common

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttachmentDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("partOf")
    val partOf: String?,

    @SerializedName("referenceId")
    val referenceId: Int?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("s3Name")
    val s3Name: String?,

    @SerializedName("fileName")
    val fileName: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("uri")
    val uri: Uri? = null,

    ) : Parcelable {
    companion object {

    }
}