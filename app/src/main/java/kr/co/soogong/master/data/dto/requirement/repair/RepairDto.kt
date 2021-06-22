package kr.co.soogong.master.data.dto.requirement.repair

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.requirement.review.ReviewDto
import java.util.*

@Parcelize
data class RepairDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("estimationId")
    val estimationId: Int?,

    @SerializedName("scheduledDate")
    val scheduledDate: Date?,

    @SerializedName("actualDate")
    val actualDate: Date?,

    @SerializedName("actualPrice")
    val actualPrice: Int?,

    @SerializedName("warrantyDueDate")
    val warrantyDueDate: Date?,

    @SerializedName("requestReviewYn")
    val requestReviewYn: Boolean?,

    @SerializedName("canceledYn")
    val canceledYn: Boolean?,

    @SerializedName("canceledReason")
    val canceledReason: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("review")
    val review: ReviewDto?,

    @SerializedName("createdAt")
    val createdAt: Date? = null,

    @SerializedName("updatedAt")
    val updatedAt: Date? = null,

) : Parcelable {
    companion object {

    }
}