package kr.co.soogong.master.data.dto.profile

import android.net.Uri
import android.os.Parcelable
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto

@Parcelize
data class PortfolioDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("masterId")
    val masterId: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("project")
    val project: String? = null,

    @SerializedName("type")
    val type: String?,

    @SerializedName("beforeImage")
    val beforeImage: AttachmentDto? = null,

    @SerializedName("afterImage")
    val afterImage: AttachmentDto? = null,

    @SerializedName("beforeImageUri")
    val beforeImageUri: Uri? = null,

    @SerializedName("afterImageUri")
    val afterImageUri: Uri? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("useYn")
    val useYn: Boolean? = null,

) : Parcelable {
    companion object {

    }
}