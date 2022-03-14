package kr.co.soogong.master.data.entity.profile.portfolio

import com.google.gson.annotations.SerializedName
import kr.co.soogong.master.data.entity.common.AttachmentDto

data class SaveRepairPhotoDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("masterId")
    val masterId: Int,

    @SerializedName("projectId")
    val projectId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("images")
    val images: List<AttachmentDto>?,

    @SerializedName("updateImages")
    val updateImages: Boolean,
    ) {
    companion object
}