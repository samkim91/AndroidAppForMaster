package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.dto.requirement.review.ReviewDto
import java.util.*

@Parcelize
@Entity(tableName = "Master")
data class MasterDto(
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,    // ID

    @SerializedName("uid")
    val uid: String? = null,

    @SerializedName("ownerName")
    val ownerName: String? = null,

    @SerializedName("tel")
    val tel: String? = null,

    @SerializedName("safetyNumber")
    val safetyNumber: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("roadAddress")
    val roadAddress: String? = null,

    @SerializedName("detailAddress")
    val detailAddress: String? = null,

    @SerializedName("latitude")
    val latitude: Float? = null,

    @SerializedName("longitude")
    val longitude: Float? = null,

    @SerializedName("serviceArea")
    val serviceArea: Int? = null,

    @SerializedName("subscriptionPlan")
    val subscriptionPlan: String? = null,

    @SerializedName("approvedStatus")
    val approvedStatus: String? = null,

    @SerializedName("public")
    val isPublic: Boolean? = null,

    @SerializedName("profileImage")
    val profileImage: AttachmentDto? = null,

    @SerializedName("introduction")
    val introduction: String? = null,

    @SerializedName("shopImages")
    val shopImages: List<AttachmentDto>? = null,

    @SerializedName("updateShopImagesYn")
    val updateShopImagesYn: Boolean? = null,

    @SerializedName("warrantyPeriod")
    val warrantyPeriod: Int? = null,

    @SerializedName("warrantyDescription")
    val warrantyDescription: String? = null,

    @SerializedName("openDate")
    val openDate: String? = null,

    @SerializedName("businessType")
    val businessType: String? = null,

    @SerializedName("businessName")
    val businessName: String? = null,

    @SerializedName("businessRegistImage")
    val businessRegistImage: AttachmentDto? = null,

    @SerializedName("shopName")
    val shopName: String? = null,

    @SerializedName("businessNumber")
    val businessNumber: String? = null,

    @SerializedName("privatePolicy")
    val privatePolicy: Boolean? = null,

    @SerializedName("marketingPush")
    val marketingPush: Boolean? = null,

    @SerializedName("pushAtNight")
    val pushAtNight: Boolean? = null,

    @SerializedName("freeMeasureYn")
    val freeMeasureYn: Boolean? = null,

    @SerializedName("secretaryYn")
    val secretaryYn: Boolean? = null,

    @SerializedName("directRepairYn")
    val directRepairYn: Boolean? = null,

    @SerializedName("requestMeasureYn")
    val requestMeasureYn: Boolean? = null,

    @SerializedName("masterConfigs")
    val masterConfigs: List<MasterConfigDto>? = null,

    @SerializedName("masterPortfolios")
    val masterPortfolios: List<PortfolioDto>? = null,

    @SerializedName("projects")
    val projects: List<ProjectDto>? = null,

    @SerializedName("repairCount")
    val repairCount: Int? = null,

    @SerializedName("reviews")
    val reviews: List<ReviewDto>? = null,

    @SerializedName("reviewCount")
    val reviewCount: Int? = null,

    @SerializedName("reviewKindnessAvg")
    val reviewKindnessAvg: Double? = null,

    @SerializedName("reviewRecommendationAvg")
    val reviewRecommendationAvg: Double? = null,

    @SerializedName("reviewQualityAvg")
    val reviewQualityAvg: Double? = null,

    @SerializedName("reviewAffordabilityAvg")
    val reviewAffordabilityAvg: Double? = null,

    @SerializedName("reviewPunctualityAvg")
    val reviewPunctualityAvg: Double? = null,

    @SerializedName("createdAt")
    val createdAt: Date? = null,

    @SerializedName("updatedAt")
    val updatedAt: Date? = null,

    ) : Parcelable {
    companion object {

    }
}
