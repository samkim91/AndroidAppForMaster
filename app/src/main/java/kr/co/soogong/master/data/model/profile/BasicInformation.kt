package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.ui.profile.*

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
                portfolios = masterDto.masterPortfolios?.filter { portfolioDto -> portfolioDto.type == PortfolioCodeTable.code },
                priceByProjects = masterDto.masterPortfolios?.filter { portfolioDto -> portfolioDto.type == PriceByProjectCodeTable.code },
                profileImage = masterDto.profileImage,
                flexibleCost = FlexibleCost.fromMasterConfigList(masterDto.masterConfigs?.filter { masterConfigDto -> masterConfigDto.groupCode == FlexibleCostCodeTable.code }),
                otherFlexibleOptions = OtherFlexibleOptions.fromMasterConfigList(masterDto.masterConfigs?.filter { masterConfigDto -> masterConfigDto.groupCode == OtherFlexibleOptionsCodeTable.code }),
                email = masterDto.email,
            )
        }
    }
}