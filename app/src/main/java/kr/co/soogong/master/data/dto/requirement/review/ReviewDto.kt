package kr.co.soogong.master.data.dto.requirement.review

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto
import java.util.*

@Parcelize
data class ReviewDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("content")
    val content: String?,

    @SerializedName("projectType")
    val projectType: String? = null,

    @SerializedName("attachments")
    val attachments: MutableList<AttachmentDto>?,

    @SerializedName("reviewScores")
    val reviewScores: List<ReviewScoreDto>?,

    @SerializedName("createdAt")
    val createdAt: Date? = null,

    @SerializedName("updatedAt")
    val updatedAt: Date? = null,
) : Parcelable {
    companion object {

    }
}