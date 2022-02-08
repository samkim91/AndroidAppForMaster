package kr.co.soogong.master.domain.entity.requirement

import kr.co.soogong.master.data.entity.requirement.RequirementCardDto
import kr.co.soogong.master.data.entity.requirement.qna.RequirementQnaDto
import java.util.*

data class RequirementCard(
    val id: Int,
    val token: String,
    val typeCode: String,
    val typeName: String,
    val projectId: Int,
    val projectName: String,
    val address: String,
    val distance: Int,
    val status: RequirementStatus,
    val subStatus: String,
    val phoneNumber: String,
    val requirementQnas: List<RequirementQnaDto>,
    val estimationId: Int,
    val masterResponseCode: String,
    val price: Int,
    val vatYn: Boolean,
    val repairId: Int,
    val repairPrice: Int,
    val repairVatYn: Boolean,
    val repairDate: Date?,
    val requestReviewYn: Boolean,
    val closedAt: Date,
    val createdAt: Date,
) {
    companion object {
        fun fromRequirementCardDto(requirementCardDto: RequirementCardDto) =
            RequirementCard(
                id = requirementCardDto.id,
                token = requirementCardDto.token,
                typeCode = requirementCardDto.typeCode,
                typeName = requirementCardDto.typeName,
                projectId = requirementCardDto.projectId,
                projectName = requirementCardDto.projectName,
                address = requirementCardDto.oldAddress ?: requirementCardDto.address,
                distance = requirementCardDto.distance,
                status = RequirementStatus.getStatusFromRequirementCardDto(requirementCardDto),
                subStatus = requirementCardDto.subStatus,
                phoneNumber = requirementCardDto.safetyNumber ?: requirementCardDto.tel,
                requirementQnas = requirementCardDto.requirementQnas,
                estimationId = requirementCardDto.estimationId,
                masterResponseCode = requirementCardDto.masterResponseCode,
                price = requirementCardDto.price,
                vatYn = requirementCardDto.vatYn,
                repairId = requirementCardDto.repairId,
                repairPrice = requirementCardDto.repairPrice,
                repairVatYn = requirementCardDto.repairVatYn,
                repairDate = requirementCardDto.repairDate,
                requestReviewYn = requirementCardDto.requestReviewYn,
                closedAt = requirementCardDto.closedAt,
                createdAt = requirementCardDto.createdAt,
            )
    }
}