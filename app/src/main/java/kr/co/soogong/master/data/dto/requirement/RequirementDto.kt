package kr.co.soogong.master.data.dto.requirement

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.qna.RequirementQnaDto
import kr.co.soogong.master.data.model.requirement.ImagePath

@Parcelize
@Entity(tableName = "Requirement")
data class RequirementDto(
    @PrimaryKey
    @SerializedName("requirementId")
    val requirementId: Int,

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

    @SerializedName("images")
    val images: MutableList<ImagePath>?,

    @SerializedName("requirementQnas")
    val requirementQnas: List<RequirementQnaDto>?,

    @SerializedName("estimationDto")
    val estimationDto: EstimationDto?,

    @SerializedName("closedAt")
    val closedAt: String?,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,
) : Parcelable {
    companion object {

    }
}
