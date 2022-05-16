package kr.co.soogong.master.data.entity.requirement

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import java.util.*

@Parcelize
@Entity(tableName = "Requirement")
data class RequirementDto(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("token")
    val token: String,

    @SerializedName("typeCode")
    val typeCode: String,

    @SerializedName("subTypeCode")
    val subTypeCode: String,

    @SerializedName("projectId")
    val projectId: Int,

    @SerializedName("projectName")
    val projectName: String?,

    @SerializedName("address")
    val address: String,

    @SerializedName("oldAddress")
    val oldAddress: String?,

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

    @SerializedName("description")
    val description: String?,

    @SerializedName("callCenterDescription")
    val callCenterDescription: String?,

    @SerializedName("requirementQnas")
    val requirementQnas: List<RequirementQnaDto>?,

    @SerializedName("estimation")
    val estimationDto: EstimationDto?,

    @SerializedName("fromRequirement")
    val previousRequirementDto: PreviousRequirementDto?,

    @SerializedName("measurement")
    val measurement: EstimationDto?,

    @SerializedName("imageUrls")
    val images: List<String>?,

    @SerializedName("canceledReasonCode")
    val canceledReasonCode: String?,

    @SerializedName("canceledReasonName")
    val canceledReasonName: String?,

    @SerializedName("canceledDescription")
    val canceledDescription: String?,

    @SerializedName("canceledBy")
    val canceledBy: String?,

    @SerializedName("closedAt")
    val closedAt: Date,

    @SerializedName("createdAt")
    val createdAt: Date,

    @SerializedName("updatedAt")
    val updatedAt: Date,
) : Parcelable {
    companion object
}
