package kr.co.soogong.master.domain.entity.requirement

import kr.co.soogong.master.data.entity.requirement.PreviousRequirementDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.RequirementQnaDto
import kr.co.soogong.master.domain.entity.requirement.estimation.Estimation
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
    val status: RequirementStatus,
    val subStatus: String,
    val phoneNumber: String,
    val requirementQnas: List<RequirementQnaDto>?,
    val description: String?,
    val estimation: Estimation,
    val previousRequirementDto: PreviousRequirementDto?,
    val measurement: Estimation?,
    val images: List<String>?,
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
                status = RequirementStatus.getStatusFromRequirementDto(requirementDto),
                subStatus = requirementDto.subStatusCode,
                phoneNumber = if (requirementDto.estimationDto?.fromClientCallCnt!! > 0 || requirementDto.estimationDto.fromMasterCallCnt!! > 0) {
                    requirementDto.tel
                } else {
                    requirementDto.safetyNumber ?: requirementDto.tel
                },
                requirementQnas = requirementDto.requirementQnas,
                description = requirementDto.description,
                estimation = Estimation.fromDto(requirementDto.estimationDto),
                previousRequirementDto = requirementDto.previousRequirementDto,
                measurement = if (requirementDto.measurement != null) Estimation.fromDto(
                    requirementDto.estimationDto) else null,
                images = requirementDto.images,
                cancelName = requirementDto.canceledReasonName,
                canceledDescription = requirementDto.canceledDescription,
                canceledBy = requirementDto.canceledBy,
                closedAt = requirementDto.closedAt,
                createdAt = requirementDto.createdAt,
                updatedAt = requirementDto.updatedAt,
            )
    }
}