package kr.co.soogong.master.data.model.requirement

import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.qna.RequirementQnaDto
import java.util.*

data class RequirementCard(
    val id: Int,
    val token: String?,
    val typeCode: String?,
    val typeName: String?,
    val project: String?,
    val address: String?,
    val oldAddress: String?,
    val distance: Int?,
    val status: RequirementStatus?,
    val subStatus: String?,
    val tel: String?,
    val safetyNumber: String?,
    val requirementQnas: List<RequirementQnaDto>?,
    val closedAt: Date?,
    val createdAt: Date?,
    val updatedAt: Date?,
    val introductionText: String?,
    val estimationDto: EstimationDto?,
) {
    companion object {
        fun fromRequirementDto(requirementDto: RequirementDto): RequirementCard {
            return RequirementCard(
                id = requirementDto.id,
                token = requirementDto.token,
                typeCode = requirementDto.typeCode,
                typeName = requirementDto.typeName,
                project = requirementDto.projectName,
                address = requirementDto.address,
                oldAddress = requirementDto.oldAddress,
                distance = requirementDto.distance,
                status = RequirementStatus.getStatusFromRequirement(requirementDto),
                subStatus = requirementDto.subStatus,
                tel = requirementDto.tel,
                safetyNumber = requirementDto.safetyNumber,
                requirementQnas = requirementDto.requirementQnas,
                closedAt = requirementDto.closedAt,
                createdAt = requirementDto.createdAt,
                updatedAt = requirementDto.updatedAt,
                introductionText = RequirementStatus.getStatusFromRequirement(requirementDto).introductionText,
                estimationDto = requirementDto.estimationDto
            )
        }
    }
}