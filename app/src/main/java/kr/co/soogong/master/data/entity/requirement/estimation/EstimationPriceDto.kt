package kr.co.soogong.master.data.entity.requirement.estimation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstimationPriceDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("estimationId")
    val estimationId: Int?,

    @SerializedName("priceTypeCode")
    val priceTypeCode: String?,

    @SerializedName("partialPrice")
    val partialPrice: Int?,
) : Parcelable {
    companion object {
        fun inputToEstimationPriceDto(estimationDto: EstimationDto?, priceType: String?, partialPrice: Int?): EstimationPriceDto {
            return EstimationPriceDto(
                id = null,
                estimationId = estimationDto?.id,
                priceTypeCode = priceType,
                partialPrice = partialPrice,
            )
        }
    }
}