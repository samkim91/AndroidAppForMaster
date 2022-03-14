package kr.co.soogong.master.domain.entity.profile.portfolio

import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.domain.entity.common.PortfolioType

interface IPortfolio {
    val id: Int
    val masterId: Int
    val type: PortfolioType
    val title: String
    val description: String

    companion object {
        fun fromDto(portfolioDto: PortfolioDto): IPortfolio {
            return when (portfolioDto.typeCode) {
                PortfolioType.PORTFOLIO.code -> Portfolio.fromDto(portfolioDto)
                PortfolioType.REPAIR_PHOTO.code -> RepairPhoto.fromDto(portfolioDto)
                else -> PriceByProject.fromDto(portfolioDto)
            }
        }
    }
}