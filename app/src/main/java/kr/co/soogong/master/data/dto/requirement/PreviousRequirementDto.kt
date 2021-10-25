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
data class PreviousRequirementDto(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("token")
    val token: String?,

    @SerializedName("canceledCode")
    val canceledCode: String? = null,

    @SerializedName("cancelName")
    val cancelName: String? = null,

    @SerializedName("canceledDescription")
    val canceledDescription: String? = null,

    @SerializedName("canceledBy")
    val canceledBy: String? = null,

    @SerializedName("createdAt")
    val createdAt: Date?,

    @SerializedName("updatedAt")
    val updatedAt: Date?,
) : Parcelable {
    companion object {

    }
}
