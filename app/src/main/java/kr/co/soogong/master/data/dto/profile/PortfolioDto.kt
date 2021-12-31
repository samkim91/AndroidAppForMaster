package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.common.AttachmentDto

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

    @SerializedName("beforeRepairImage")
    val beforeImage: AttachmentDto? = null,

    @SerializedName("afterRepairImage")
    val afterImage: AttachmentDto? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("useYn")
    val useYn: Boolean? = null,

    ) : Parcelable {
    companion object
}