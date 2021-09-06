package kr.co.soogong.master.data.model.requirement

import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import java.util.*

data class RequirementCard(
    val id: Int,
    val token: String?,
    val typeCode: String?,
    val typeName: String?,
    val project: String?,
    val address: String?,
    val status: RequirementStatus?,
    val tel: String?,
    val measuringDate: Date?,
    val closedAt: Date?,
    val createdAt: Date?,
    val updatedAt: Date?,
    val introductionText: String?,
    val estimationDto: EstimationDto?
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
                status = RequirementStatus.getStatusFromRequirement(requirementDto),
                tel = requirementDto.tel,
                measuringDate = requirementDto.measuringDate,
                closedAt = requirementDto.closedAt,
                createdAt = requirementDto.createdAt,
                updatedAt = requirementDto.updatedAt,
                introductionText = RequirementStatus.getStatusFromRequirement(requirementDto).introductionText,
                estimationDto = requirementDto.estimationDto
            )
        }
    }
}