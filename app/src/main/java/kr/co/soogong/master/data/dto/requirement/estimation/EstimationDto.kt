package kr.co.soogong.master.data.dto.requirement.estimation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.requirement.review.ReviewDto

@Parcelize
data class EstimationDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("requirementId")
    val requirementId: Int,

    @SerializedName("masterId")
    val masterId: Int,

    @SerializedName("token")
    val token: String,

    @SerializedName("projectName")
    val type: String,

    @SerializedName("masterResponseCode")
    val masterResponseCode: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("choosenYn")
    val choosenYn: Boolean,

    @SerializedName("estimationPrice")
    val estimationPrice: EstimationPriceDto?,

    @SerializedName("review")
    val review: ReviewDto?,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
) : Parcelable {
    companion object {

    }
}