package kr.co.soogong.master.domain.entity.profile.portfolio

import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.domain.entity.DtoConverter
import kr.co.soogong.master.domain.entity.common.PortfolioType

interface IPortfolio {
    val id: Int
    val masterId: Int
    val type: PortfolioType
    val title: String
    val description: String

    companion object : DtoConverter<PortfolioDto, IPortfolio> {
        override fun fromDto(dto: PortfolioDto): IPortfolio {
            return when (dto.typeCode) {
                PortfolioType.PORTFOLIO.code -> Portfolio.fromDto(dto)
                PortfolioType.REPAIR_PHOTO.code -> RepairPhoto.fromDto(dto)
                else -> PriceByProject.fromDto(dto)
            }
        }
    }
}