package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.global.CodeTable

@Parcelize
data class BasicInformation(
    val freeMeasureYn: Boolean?,
    val portfolios: List<PortfolioDto>?,
    val priceByProjects: List<PortfolioDto>?,
    val profileImage: AttachmentDto?,
    val masterConfigs: List<MasterConfigDto>?,
    val email: String?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): BasicInformation {
            return BasicInformation(
                freeMeasureYn = masterDto.freeMeasureYn,
                portfolios = masterDto.masterPortfolioDtos?.filter { portfolioDto -> portfolioDto.type == CodeTable.PORTFOLIO.code },
                priceByProjects = masterDto.masterPortfolioDtos?.filter { portfolioDto -> portfolioDto.type == CodeTable.PRICE_BY_PROJECT.code },
                profileImage = masterDto.profileImage,
                masterConfigs = masterDto.masterConfigDtos,
                email = masterDto.email,
            )
        }
    }
}