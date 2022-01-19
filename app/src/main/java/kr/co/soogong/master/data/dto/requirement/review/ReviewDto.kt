package kr.co.soogong.master.data.dto.requirement.review

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.common.AttachmentDto
import java.util.*

@Parcelize
data class ReviewDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("customerName")
    val customerName: String?,

    @SerializedName("customerImage")
    val customerImage: AttachmentDto?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("projectName")
    val projectName: String?,

    @SerializedName("attachments")
    val attachments: MutableList<AttachmentDto>?,

    @SerializedName("reviewScores")
    val reviewScores: List<ReviewScoreDto>?,

    @SerializedName("createdAt")
    val createdAt: Date,

    @SerializedName("updatedAt")
    val updatedAt: Date,
) : Parcelable {
    companion object
}