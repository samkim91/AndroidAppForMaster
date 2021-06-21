package kr.co.soogong.master.data.dto.requirement.estimation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EstimationPriceDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("estimationId")
    val estimationId: Int?,

    @SerializedName("priceType")
    val priceType: String?,

    @SerializedName("partialPrice")
    val partialPrice: Int?,
) : Parcelable {
    companion object {
        fun inputToEstimationPriceDto(estimationDto: EstimationDto?, priceType: String?, partialPrice: Int?): EstimationPriceDto {
            return EstimationPriceDto(
                id = null,
                estimationId = estimationDto?.id,
                priceType = priceType,
                partialPrice = partialPrice,
            )
        }
    }
}