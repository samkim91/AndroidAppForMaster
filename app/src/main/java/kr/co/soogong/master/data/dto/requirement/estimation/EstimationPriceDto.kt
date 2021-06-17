package kr.co.soogong.master.data.dto.requirement.estimation

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

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
    val createdAt: Date,

    @SerializedName("updatedAt")
    val updatedAt: Date,
) : Parcelable {
    companion object {

    }
}