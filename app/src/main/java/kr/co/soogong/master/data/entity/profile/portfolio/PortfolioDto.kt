package kr.co.soogong.master.data.entity.profile.portfolio

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.common.AttachmentDto

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

    @SerializedName("projectId")
    val projectId: Int? = null,

    @SerializedName("projectName")
    val projectName: String? = null,

    @SerializedName("typeCode")
    val typeCode: String?,

    @SerializedName("updateImages")
    val updateImages: Boolean? = null,

    @SerializedName("beforeRepairImage")
    val beforeImage: AttachmentDto? = null,

    @SerializedName("afterRepairImage")
    val afterImage: AttachmentDto? = null,

    @SerializedName("images")
    val images: List<AttachmentDto>? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("useYn")
    val useYn: Boolean? = null,

    ) : Parcelable {
    companion object
}