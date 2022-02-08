package kr.co.soogong.master.data.entity.requirement.review

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ReviewDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nickname")
    val customerNickname: String?,

    @SerializedName("profileImageUrl")
    val customerProfileImageUrl: String?,

    @SerializedName("projectName")
    val projectName: String?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("imageUrls")
    val images: List<String>?,

    @SerializedName("recommendation")
    val recommendation: Float?,

    @SerializedName("quality")
    val quality: Float?,

    @SerializedName("affordability")
    val affordability: Float?,

    @SerializedName("kindness")
    val kindness: Float?,

    @SerializedName("punctuality")
    val punctuality: Float?,

    @SerializedName("createdAt")
    val createdAt: Date,
) : Parcelable {
    companion object
}