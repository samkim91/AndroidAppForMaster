package kr.co.soogong.master.data.model.requirement

import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.dto.requirement.PreviousRequirementDto
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.qna.RequirementQnaDto
import java.util.*

data class Requirement(
    val id: Int,
    val token: String,
    val typeCode: String,
    val typeName: String,
    val projectId: Int,
    val projectName: String?,
    val address: String,
    val oldAddress: String?,
    val distance: Int?,
    val status: RequirementStatus,
    val subStatus: String,
    val phoneNumber: String,
    val requirementQnas: List<RequirementQnaDto>?,
    val description: String?,
    val estimationDto: EstimationDto?,
    val previousRequirementDto: PreviousRequirementDto?,
    val measurement: EstimationDto?,
    val images: MutableList<AttachmentDto>?,
    val canceledCode: String?,
    val cancelName: String?,
    val canceledDescription: String?,
    val canceledBy: String?,
    val closedAt: Date,
    val createdAt: Date,
    val updatedAt: Date,
) {
    companion object {
        fun fromRequirementDto(requirementDto: RequirementDto) =
            Requirement(
                id = requirementDto.id,
                token = requirementDto.token,
                typeCode = requirementDto.typeCode,
                typeName = requirementDto.typeName,
                projectId = requirementDto.projectId,
                projectName = requirementDto.projectName,
                address = requirementDto.address,
                oldAddress = requirementDto.oldAddress,
                distance = requirementDto.distance,
                status = RequirementStatus.getStatusFromRequirementDto(requirementDto),
                subStatus = requirementDto.subStatus,
                phoneNumber = requirementDto.safetyNumber ?: requirementDto.tel,
                requirementQnas = requirementDto.requirementQnas,
                description = requirementDto.description,
                estimationDto = requirementDto.estimationDto,
                previousRequirementDto = requirementDto.previousRequirementDto,
                measurement = requirementDto.measurement,
                images = requirementDto.images,
                canceledCode = requirementDto.canceledCode,
                cancelName = requirementDto.cancelName,
                canceledDescription = requirementDto.canceledDescription,
                canceledBy = requirementDto.canceledBy,
                closedAt = requirementDto.closedAt,
                createdAt = requirementDto.createdAt,
                updatedAt = requirementDto.updatedAt,
            )
    }
}