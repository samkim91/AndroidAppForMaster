package kr.co.soogong.master.data.model.requirement

import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import java.util.*

data class RequirementCard(
    val id: Int,
    val token: String?,
    val project: String?,
    val address: String?,
    val status: String?,
    val tel: String?,
    val closedAt: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val introductionText: String?,
    val estimation: EstimationDto?
) {
    companion object {
        fun fromRequirementDto(requirementDto: RequirementDto): RequirementCard {
            return RequirementCard(
                id = requirementDto.requirementId,
                token = requirementDto.token,
                project = requirementDto.projectName,
                address = requirementDto.address,
                status = RequirementStatus.getStatus(requirementDto.status).toString(),
                tel = requirementDto.tel,
                closedAt = requirementDto.closedAt,
                createdAt = requirementDto.createdAt,
                updatedAt = requirementDto.updatedAt,
                introductionText = RequirementStatus.getStatus(requirementDto.status).getIntroductionText(),
                estimation = requirementDto.estimationDto
            )
        }
    }
}