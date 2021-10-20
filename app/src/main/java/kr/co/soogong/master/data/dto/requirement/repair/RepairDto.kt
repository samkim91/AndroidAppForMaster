package kr.co.soogong.master.data.dto.requirement.repair

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.requirement.review.ReviewDto
import java.util.*

@Parcelize
data class RepairDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("requirementToken")
    val requirementToken: String? = null,

    @SerializedName("estimationId")
    val estimationId: Int? = null,

    @SerializedName("scheduledDate")
    val scheduledDate: Date? = null,

    @SerializedName("actualDate")
    val actualDate: Date? = null,

    @SerializedName("actualPrice")
    val actualPrice: Int? = null,

    @SerializedName("vatYn")
    val includingVat: Boolean? = null,

    @SerializedName("warrantyDueDate")
    val warrantyDueDate: Date? = null,

    @SerializedName("requestReviewYn")
    val requestReviewYn: Boolean? = null,

    @SerializedName("canceledYn")
    val canceledYn: Boolean? = null,

    @SerializedName("canceledCode")
    val canceledCode: String? = null,

    @SerializedName("canceledDescription")
    val canceledDescription: String? = null,

    @SerializedName("review")
    val review: ReviewDto? = null,

    @SerializedName("createdAt")
    val createdAt: Date? = null,

    @SerializedName("updatedAt")
    val updatedAt: Date? = null,

    ) : Parcelable {
    companion object {

    }
}