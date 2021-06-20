package kr.co.soogong.master.data.dto.requirement.review

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto
import java.util.*

@Parcelize
data class ReviewDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("content")
    val content: String?,

    @SerializedName("projectType")
    val projectType: String?,

    @SerializedName("attachments")
    val attachments: MutableList<AttachmentDto>?,

    @SerializedName("reviewScores")
    val reviewScores: List<ReviewScoreDto>?,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,
) : Parcelable {
    companion object {

    }
}