package kr.co.soogong.master.data.entity.requirement

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class RequirementCardDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("token")
    val token: String,

    @SerializedName("typeCode")
    val typeCode: String,

    @SerializedName("typeName")
    val typeName: String,

    @SerializedName("projectId")
    val projectId: Int,

    @SerializedName("projectName")
    val projectName: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("oldAddress")
    val oldAddress: String?,

    @SerializedName("detailAddress")
    val detailAddress: String?,

    @SerializedName("distance")
    val distance: Double,

    @SerializedName("statusCode")
    val statusCode: String,

    @SerializedName("statusName")
    val statusName: String,

    @SerializedName("subStatusCode")
    val subStatusCode: String,

    @SerializedName("subStatusName")
    val subStatusName: String,

    @SerializedName("tel")
    val tel: String,

    @SerializedName("safetyNumber")
    val safetyNumber: String? = null,

    @SerializedName("contactYn")
    val contactYn: Boolean,

    @SerializedName("requirementQnas")
    val requirementQnas: List<RequirementQnaDto>,

    @SerializedName("requestConsultingYn")
    val requestConsultingYn: Boolean,

    @SerializedName("estimationId")
    val estimationId: Int,

    @SerializedName("masterResponseCode")
    val masterResponseCode: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("vatYn")
    val vatYn: Boolean,

    @SerializedName("repairId")
    val repairId: Int,

    @SerializedName("repairPrice")
    val repairPrice: Int,

    @SerializedName("repairVatYn")
    val repairVatYn: Boolean,

    @SerializedName("repairDate")
    val repairDate: Date? = null,

    @SerializedName("requestReviewYn")
    val requestReviewYn: Boolean,

    @SerializedName("closedAt")
    val closedAt: Date,

    @SerializedName("createdAt")
    val createdAt: Date,
) : Parcelable {
    companion object
}
