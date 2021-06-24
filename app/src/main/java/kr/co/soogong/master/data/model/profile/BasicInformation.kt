package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto

@Parcelize
data class BasicInformation(
    val portfolios: List<PortfolioDto>?,
    val priceByProjects: List<PortfolioDto>?,
    val profileImage: AttachmentDto?,
    val flexibleCost: FlexibleCost?,
    val otherFlexibleOptions: OtherFlexibleOptions?,
    val email: String?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): BasicInformation {
            return BasicInformation(
                portfolios = masterDto.masterPortfolios?.filter { portfolioDto -> portfolioDto.type == "portfolio" },
                priceByProjects = masterDto.masterPortfolios?.filter { portfolioDto -> portfolioDto.type == "price" },
                profileImage = masterDto.profileImage,
                flexibleCost = FlexibleCost.fromMasterConfigList(masterDto.masterConfigs?.filter { masterConfigDto -> masterConfigDto.configGroupCode == "flexibleCost" }),
                otherFlexibleOptions = OtherFlexibleOptions.fromMasterConfigList(masterDto.masterConfigs?.filter { masterConfigDto -> masterConfigDto.configGroupCode == "otherFlexibleOption" }),
                email = masterDto.email,
            )
        }

        val NULL_BASIC_INFORMATION = BasicInformation(
            profileImage = null,
            portfolios = emptyList(),
            priceByProjects = emptyList(),
            flexibleCost = FlexibleCost.NULL_FLEXIBLE_COST,
            otherFlexibleOptions = OtherFlexibleOptions.NULL_OTHER_FLEXIBLE_OPTIONS,
            email = "",
        )
    }
}