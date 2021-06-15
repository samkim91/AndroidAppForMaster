package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kr.co.soogong.master.data.model.major.Major
import java.util.*

@Parcelize
@Entity(tableName = "Master")
data class MasterDto(
    @SerializedName("id")
    val id: Int,    // ID

    @PrimaryKey
    @SerializedName("uId")
    val uId: String,

    @SerializedName("ownerName")
    val ownerName: String?,

    @SerializedName("tel")
    val tel: String,

    @SerializedName("safetyNumber")
    val safetyNumber: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("roadAddress")
    val roadAddress: String?,

    @SerializedName("detailAddress")
    val detailAddress: String?,

    @SerializedName("latitude")
    val latitude: Float?,

    @SerializedName("longitude")
    val longitude: Float?,

    @SerializedName("serviceArea")
    val serviceArea: Int?,

    @SerializedName("subscriptionPlan")
    val subscriptionPlan: String?,

    @SerializedName("isPublic")
    val isPublic: Boolean,

    @SerializedName("profileImageId")
    val profileImageId: String?,

    @SerializedName("introduction")
    val introduction: String?,

    @SerializedName("warrantyPeriod")
    val warrantyPeriod: Int?,

    @SerializedName("warrantyDescription")
    val warrantyDescription: String?,

    @SerializedName("openDate")
    val openDate: Date?,

    @SerializedName("businessType")
    val businessType: String?,

    @SerializedName("businessName")
    val businessName: String?,

    @SerializedName("businessRegistImageId")
    val businessRegistImageId: Int?,

    @SerializedName("shopName")
    val shopName: String?,

    @SerializedName("businessNumber")
    val businessNumber: String?,

    @SerializedName("privatePolicy")
    val privatePolicy: Boolean?,

    @SerializedName("marketingPush")
    val marketingPush: Boolean?,

    @SerializedName("marketingPushAtNight")
    val marketingPushAtNight: Boolean?,

    @SerializedName("appPush")
    val appPush: Boolean?,

    @SerializedName("kakaoPush")
    val kakaoPush: Boolean?,

    @SerializedName("smsPush")
    val smsPush: Boolean?,

    @SerializedName("masterConfigs")
    val masterConfigs: @RawValue List<MasterConfigDto>?,

    @SerializedName("masterPortfolios")
    val masterPortfolios: List<PortfolioDto>?,

    @SerializedName("projects")
    val projects: List<Major>?,

    @SerializedName("createdAt")
    val createdAt: Date,

    @SerializedName("updatedAt")
    val updatedAt: Date,

    ) : Parcelable {
    companion object {

    }
}
