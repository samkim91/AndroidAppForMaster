package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto

@Parcelize
data class BasicInformation(
    val freeMeasureYn: Boolean?,
    val portfolios: List<PortfolioDto>?,
    val priceByProjects: List<PortfolioDto>?,
    val profileImage: AttachmentDto?,
    val flexibleCost: List<MasterConfigDto>?,
    val otherFlexibleOption: List<MasterConfigDto>?,
    val email: String?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): BasicInformation {
            return BasicInformation(
                freeMeasureYn = masterDto.freeMeasureYn,
                portfolios = masterDto.masterPortfolios?.filter { portfolioDto -> portfolioDto.type == CodeTable.PORTFOLIO.code },
                priceByProjects = masterDto.masterPortfolios?.filter { portfolioDto -> portfolioDto.type == CodeTable.PRICE_BY_PROJECT.code },
                profileImage = masterDto.profileImage,
                flexibleCost = masterDto.masterConfigs?.filter { masterConfigDto -> masterConfigDto.groupCode == CodeTable.FLEXIBLE_COST.code },
                otherFlexibleOption = masterDto.masterConfigs?.filter { masterConfigDto -> masterConfigDto.groupCode == CodeTable.OTHER_FLEXIBLE_OPTION.code },
                email = masterDto.email,
            )
        }
    }
}