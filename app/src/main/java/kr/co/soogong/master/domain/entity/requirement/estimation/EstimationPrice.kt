package kr.co.soogong.master.domain.entity.requirement.estimation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationPriceDto
import kr.co.soogong.master.domain.entity.DtoConverter
import kr.co.soogong.master.domain.entity.common.CodeTable

@Parcelize
data class EstimationPrice(
    val id: Int,
    val priceTypeCode: CodeTable?,
    val partialPrice: Int,
) : Parcelable {


    companion object : DtoConverter<EstimationPriceDto, EstimationPrice> {
        override fun fromDto(dto: EstimationPriceDto): EstimationPrice {
            return EstimationPrice(
                id = dto.id!!,
                priceTypeCode = CodeTable.getCodeTableByCode(dto.priceTypeCode!!),
                partialPrice = dto.partialPrice!!,
            )
        }
    }
}
