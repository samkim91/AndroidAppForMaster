package kr.co.soogong.master.domain.entity.profile.portfolio

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.domain.entity.common.PortfolioType
import kr.co.soogong.master.domain.entity.common.major.Project

@Parcelize
data class RepairPhoto(
    override val id: Int,
    override val masterId: Int,
    override val type: PortfolioType,
    override val title: String,
    override val description: String,
    val project: Project,
    val images: List<AttachmentDto>?,
) : IPortfolio, Parcelable {
    companion object {
        fun fromDto(portfolioDto: PortfolioDto): RepairPhoto =
            RepairPhoto(
                id = portfolioDto.id!!,
                masterId = portfolioDto.masterId!!,
                project = Project(id = portfolioDto.projectId!!, name = portfolioDto.projectName!!),
                type = PortfolioType.getPortfolioTypeFromCode(portfolioDto.typeCode!!),
                title = portfolioDto.title!!,
                description = portfolioDto.description ?: "",
                images = portfolioDto.images,
            )
    }
}