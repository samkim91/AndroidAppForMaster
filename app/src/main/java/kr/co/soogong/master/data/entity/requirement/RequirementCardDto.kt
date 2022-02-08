package kr.co.soogong.master.data.entity.requirement

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.requirement.qna.RequirementQnaDto
import java.util.*

@Parcelize
//@Entity(tableName = "RequirementCard")
data class RequirementCardDto(
//    @PrimaryKey
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

    @SerializedName("distance")
    val distance: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("subStatus")
    val subStatus: String,

    @SerializedName("tel")
    val tel: String,

    @SerializedName("safetyNumber")
    val safetyNumber: String? = null,

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
