package kr.co.soogong.master.data.dto.requirement.estimation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.requirement.repair.RepairDto
import java.util.*

@Parcelize
data class EstimationDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("token")
    val token: String?,

    @SerializedName("requirementId")
    val requirementId: Int?,

    @SerializedName("masterId")
    val masterId: Int?,

    @SerializedName("masterResponseCode")
    val masterResponseCode: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("choosenYn")
    val choosenYn: Boolean?,

    @SerializedName("estimationPrice")
    val estimationPrice: List<EstimationPriceDto>?,

    @SerializedName("repair")
    val repair: RepairDto?,

    @SerializedName("createdAt")
    val createdAt: Date?,

    @SerializedName("updatedAt")
    val updatedAt: Date?,
) : Parcelable {
    companion object {

    }
}