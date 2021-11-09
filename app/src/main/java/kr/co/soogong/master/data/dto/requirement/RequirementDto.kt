package kr.co.soogong.master.data.dto.requirement

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.qna.RequirementQnaDto
import java.util.*

@Parcelize
@Entity(tableName = "Requirement")
data class RequirementDto(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("token")
    val token: String?,

    @SerializedName("uid")
    val uid: String?,

    @SerializedName("typeCode")
    val typeCode: String?,

    @SerializedName("typeName")
    val typeName: String?,

    @SerializedName("projectId")
    val projectId: Int?,

    @SerializedName("projectName")
    val projectName: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("oldAddress")
    val oldAddress: String?,

    @SerializedName("distance")
    val distance: Int? = null,

    @SerializedName("status")
    val status: String?,

    @SerializedName("subStatus")
    val subStatus: String?,

    @SerializedName("tel")
    val tel: String?,

    @SerializedName("safetyNumber")
    val safetyNumber: String? = null,

    @SerializedName("description")
    val description: String?,

    @SerializedName("requirementQnas")
    val requirementQnas: List<RequirementQnaDto>?,

    @SerializedName("estimation")
    val estimationDto: EstimationDto?,

    @SerializedName("fromRequirement")
    val previousRequirementDto: PreviousRequirementDto?,

    @SerializedName("measurement")
    val measurement: EstimationDto?,

    @SerializedName("images")
    val images: MutableList<AttachmentDto>?,

    @SerializedName("canceledCode")
    val canceledCode: String? = null,

    @SerializedName("cancelName")
    val cancelName: String? = null,

    @SerializedName("canceledDescription")
    val canceledDescription: String? = null,

    @SerializedName("canceledBy")
    val canceledBy: String? = null,

    @SerializedName("closedAt")
    val closedAt: Date?,

    @SerializedName("createdAt")
    val createdAt: Date?,

    @SerializedName("updatedAt")
    val updatedAt: Date?,
) : Parcelable {
    companion object {

    }
}
