package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import kr.co.soogong.master.data.model.requirement.ImagePath

@Parcelize
data class BasicInformation(
    val portfolios: List<PortfolioDto>?,
    val priceByProjects: List<PortfolioDto>?,
    val profileImage: ImagePath?,
    val flexibleCost: FlexibleCost?,
    val otherFlexibleOptions: OtherFlexibleOptions?,
    val email: String?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): BasicInformation {
            return BasicInformation(
                portfolios = masterDto.masterPortfolios?.filter { portfolioDto -> portfolioDto.type == "portfolio" },
                priceByProjects = masterDto.masterPortfolios?.filter { portfolioDto -> portfolioDto.type == "price" },
                profileImage = ImagePath(masterDto.profileImage),
                flexibleCost = FlexibleCost.fromMasterConfigList(masterDto.masterConfigs?.filter { masterConfigDto -> masterConfigDto.configGroupCode == "flexibleCost" }),
                otherFlexibleOptions = OtherFlexibleOptions.fromMasterConfigList(masterDto.masterConfigs?.filter { masterConfigDto -> masterConfigDto.configGroupCode == "otherFlexibleOption" }),
                email = masterDto.email,
            )
        }


//        fun fromJson(jsonObject: JsonObject): BasicInformation {
//            val item = jsonObject.get("data").asJsonObject
//
//            return BasicInformation(
//                subscriptionPlan = item.get("subscriptionPlan").asString,
//                profileImage = ImagePath.fromJson(item.get("profileImage").asJsonObject),
//                myReviews = MyReview.fromJson(item.get("myReviews").asJsonObject),
//                myPageUrl = item.get("myPageUrl").asString,
//                portfolios =  item.get("portfolio").asJsonArray.map { Portfolio.fromJson(it.asJsonObject) },
//                priceByProjects = item.get("priceByProject").asJsonArray.map { PriceByProject.fromJson(it.asJsonObject) },
//                flexibleCost = FlexibleCost.fromJson(item.get("flexibleCost").asJsonObject),
//                otherFlexibleOptions = OtherFlexibleOptions.fromJson(item.get("otherFlexibleOptions").asJsonObject),
//                email = item.get("email").asString,
//            )
//        }
//
//        val TEST_BASIC_INFORMATION = BasicInformation(
//            subscriptionPlan = "게런티 마스터",
//            profileImage = ImagePath.TEST_IMAGE_PATH,
//            myReviews = MyReview.TEST_MY_REVIEW,
//            myPageUrl = "test url",
//            portfolios =  listOf(Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO),
//            priceByProjects = listOf(PriceByProject.TEST_PRICE_BY_PROJECT, PriceByProject.TEST_PRICE_BY_PROJECT, PriceByProject.TEST_PRICE_BY_PROJECT),
//            flexibleCost = FlexibleCost.TEST_FLEXIBLE_COST,
//            otherFlexibleOptions = OtherFlexibleOptions.TEST_OTHER_FLEXIBLE_OPTIONS,
//            email = "test@soogong.co.kr",
//        )
//
        val NULL_BASIC_INFORMATION = BasicInformation(
            profileImage = ImagePath.NULL_IMAGE_PATH,
            portfolios =  emptyList(),
            priceByProjects = emptyList(),
            flexibleCost = FlexibleCost.NULL_FLEXIBLE_COST,
            otherFlexibleOptions = OtherFlexibleOptions.NULL_OTHER_FLEXIBLE_OPTIONS,
            email = "",
        )
    }
}