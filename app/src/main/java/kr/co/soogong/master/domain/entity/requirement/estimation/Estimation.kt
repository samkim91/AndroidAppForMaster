package kr.co.soogong.master.domain.entity.requirement.estimation

import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.domain.entity.DtoConverter
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.requirement.repair.Repair
import java.util.*

data class Estimation(
    val id: Int,
    val token: String,
    val requirementId: Int,
    val masterId: Int,
    val masterResponseCode: CodeTable,
    val typeCode: CodeTable?,
    val price: Int,
    val includingVat: Boolean,
    val description: String,
    val masterMemo: String,
    val fromMasterCallCnt: Int,
    val fromClientCallCnt: Int,
    val estimationPrices: List<EstimationPrice>,
    val images: List<String>,
    val repair: Repair?,
    val visitDate: Date?,
    val createdAt: Date,
) {

    companion object : DtoConverter<EstimationDto, Estimation> {
        override fun fromDto(dto: EstimationDto): Estimation {
            return Estimation(
                id = dto.id!!,
                token = dto.token!!,
                requirementId = dto.requirementId!!,
                masterId = dto.masterId!!,
                masterResponseCode = CodeTable.getCodeTableByCode(dto.masterResponseCode ?: "")
                    ?: CodeTable.DEFAULT,
                typeCode = CodeTable.getCodeTableByCode(dto.typeCode ?: ""),
                price = dto.price ?: 0,
                includingVat = dto.includingVat ?: false,
                description = dto.description ?: "",
                masterMemo = dto.masterMemo ?: "",
                fromMasterCallCnt = dto.fromMasterCallCnt ?: 0,
                fromClientCallCnt = dto.fromClientCallCnt ?: 0,
                estimationPrices = if (dto.estimationPrices.isNullOrEmpty()) listOf()
                else dto.estimationPrices.map { estimationPriceDto ->
                    EstimationPrice.fromDto(estimationPriceDto)
                },
                images = dto.images ?: listOf(),
                repair = if (dto.repair != null) Repair.fromDto(dto.repair) else null,
                visitDate = dto.visitDate,
                createdAt = dto.createdAt!!,
            )
        }
    }
}
