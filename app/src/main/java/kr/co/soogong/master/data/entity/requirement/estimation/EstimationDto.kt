package kr.co.soogong.master.data.entity.requirement.estimation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
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
    val typeCode: String?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("vatYn")
    val includingVat: Boolean? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("isSavingTemplate")
    val isSavingTemplate: Boolean = false,

    @SerializedName("requestConsultingYn")
    val requestConsultingYn: Boolean? = null,

    @SerializedName("fromMasterCallCnt")
    val fromMasterCallCnt: Int? = null,

    @SerializedName("fromClientCallCnt")
    val fromClientCallCnt: Int? = null,

    @SerializedName("choosenYn")
    val choosenYn: Boolean? = null,

    @SerializedName("estimationPrices")
    val estimationPrices: List<EstimationPriceDto>? = emptyList(),

    @SerializedName("imageUrls")
    val images: MutableList<String>? = mutableListOf(),

    @SerializedName("repair")
    val repair: RepairDto? = null,

    @SerializedName("refuseCode")
    val refuseCode: String? = null,

    @SerializedName("refuseDescription")
    val refuseDescription: String? = null,

    @SerializedName("createdAt")
    val createdAt: Date?,

    @SerializedName("updatedAt")
    val updatedAt: Date?,
) : Parcelable {
    companion object
}