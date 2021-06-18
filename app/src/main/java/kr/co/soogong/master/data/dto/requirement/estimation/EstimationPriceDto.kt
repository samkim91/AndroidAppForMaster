package kr.co.soogong.master.data.dto.requirement.estimation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstimationPriceDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("estimationId")
    val estimationId: String,

    @SerializedName("partialPrice")
    val partialPrice: Int,

    @SerializedName("priceType")
    val priceType: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
) : Parcelable {
    companion object {

    }
}