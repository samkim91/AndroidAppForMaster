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

    @SerializedName("projectId")
    val projectId: Int?,

    @SerializedName("projectName")
    val projectName: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("tel")
    val tel: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("requirementQnas")
    val requirementQnas: List<RequirementQnaDto>?,

    @SerializedName("estimation")
    val estimationDto: EstimationDto?,

    @SerializedName("images")
    val images: MutableList<AttachmentDto>?,

    @SerializedName("canceledYn")
    val canceledYn: Boolean? = null,

    @SerializedName("canceledReason")
    val canceledReason: String? = null,

    @SerializedName("canceledDescription")
    val canceledDescription: String? = null,

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
