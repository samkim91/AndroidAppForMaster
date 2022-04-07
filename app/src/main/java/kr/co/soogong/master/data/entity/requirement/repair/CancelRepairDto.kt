package kr.co.soogong.master.data.entity.requirement.repair

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CancelRepairDto(
    @SerializedName("requirementToken")
    val requirementToken: String,

    @SerializedName("estimationId")
    val estimationId: Int,

    @SerializedName("canceledYn")
    val canceledYn: Boolean,

    @SerializedName("canceledCode")
    val canceledCode: String,

    @SerializedName("canceledDescription")
    val canceledDescription: String,
) : Parcelable {
    companion object
}