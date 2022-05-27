package kr.co.soogong.master.domain.entity.requirement

import kr.co.soogong.master.data.entity.requirement.PreviousRequirementDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.RequirementQnaDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.requirement.estimation.Estimation
import kr.co.soogong.master.utility.LocationHelper
import java.util.*

data class Requirement(
    val id: Int,
    val token: String,
    val typeCode: CodeTable,
    val projectId: Int,
    val projectName: String?,
    val address: String,
    val oldAddress: String?,
    val status: RequirementStatus,
    val subStatus: String,
    val phoneNumber: String,
    val requirementQnas: List<RequirementQnaDto>?,
    val description: String,
    val callCenterDescription: String,
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
                typeCode = CodeTable.getCodeTableByCode(requirementDto.typeCode)
                    ?: CodeTable.COMPARE,
                projectId = requirementDto.projectId,
                projectName = requirementDto.projectName,
                address = LocationHelper.combineAddressWithDetail(requirementDto.address, requirementDto.detailAddress),
                oldAddress = if (!requirementDto.oldAddress.isNullOrEmpty()) LocationHelper.combineAddressWithDetail(requirementDto.oldAddress, requirementDto.detailAddress) else "",
                status = RequirementStatus.getStatusFromRequirementDto(requirementDto),
                subStatus = requirementDto.subStatusCode,
                phoneNumber = if (requirementDto.estimationDto?.fromClientCallCnt!! > 0 || requirementDto.estimationDto.fromMasterCallCnt!! > 0) {
                    requirementDto.tel
                } else {
                    requirementDto.safetyNumber ?: requirementDto.tel
                },
                requirementQnas = requirementDto.requirementQnas,
                description = requirementDto.description ?: "",
                callCenterDescription = requirementDto.callCenterDescription ?: "",
                estimation = Estimation.fromDto(requirementDto.estimationDto),
                previousRequirementDto = requirementDto.previousRequirementDto,
                measurement = if (requirementDto.measurement != null) Estimation.fromDto(
                    requirementDto.measurement) else null,
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