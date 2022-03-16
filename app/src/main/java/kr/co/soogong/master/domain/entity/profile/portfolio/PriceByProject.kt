package kr.co.soogong.master.domain.entity.profile.portfolio

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.domain.entity.common.PortfolioType

@Parcelize
data class PriceByProject(
    override val id: Int,
    override val masterId: Int,
    override val type: PortfolioType,
    override val title: String,
    override val description: String,
    val price: Int,
) : IPortfolio, Parcelable {
    companion object {
        fun fromDto(portfolioDto: PortfolioDto): PriceByProject =
            PriceByProject(
                id = portfolioDto.id!!,
                masterId = portfolioDto.masterId!!,
                type = PortfolioType.getPortfolioTypeFromCode(portfolioDto.typeCode!!),
                title = portfolioDto.title!!,
                description = portfolioDto.description ?: "",
                price = portfolioDto.price!!
            )
    }
}