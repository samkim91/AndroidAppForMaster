package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.estimation.ImagePath

@Parcelize
data class BasicInformation(
    val subscriptionPlan: String?,
    val profileImage: ImagePath?,
    val myReviews: MyReview?,
    val myPageUrl: String,
    val portfolios: List<Portfolio>?,
    val priceByProjects: List<PriceByProject>?,
    val flexibleCost: FlexibleCost?,
    val otherFlexibleOptions: OtherFlexibleOptions?,
    val email: String?,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): BasicInformation{
            val item = jsonObject.get("data").asJsonObject

            return BasicInformation(
                subscriptionPlan = item.get("subscriptionPlan").asString,
                profileImage = ImagePath.fromJson(item.get("profileImage").asJsonObject),
                myReviews = MyReview.fromJson(item.get("myReviews").asJsonObject),
                myPageUrl = item.get("myPageUrl").asString,
                portfolios =  item.get("portfolio").asJsonArray.map { Portfolio.fromJson(it.asJsonObject) },
                priceByProjects = item.get("priceByProject").asJsonArray.map { PriceByProject.fromJson(it.asJsonObject) },
                flexibleCost = FlexibleCost.fromJson(item.get("flexibleCost").asJsonObject),
                otherFlexibleOptions = OtherFlexibleOptions.fromJson(item.get("otherFlexibleOptions").asJsonObject),
                email = item.get("email").asString,
            )
        }

        val TEST_BASIC_INFORMATION = BasicInformation(
            subscriptionPlan = "게런티 마스터",
            profileImage = ImagePath.TEST_IMAGE_PATH,
            myReviews = MyReview.TEST_MY_REVIEW,
            myPageUrl = "test url",
            portfolios =  listOf(Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO),
            priceByProjects = listOf(PriceByProject.TEST_PRICE_BY_PROJECT, PriceByProject.TEST_PRICE_BY_PROJECT, PriceByProject.TEST_PRICE_BY_PROJECT),
            flexibleCost = FlexibleCost.TEST_FLEXIBLE_COST,
            otherFlexibleOptions = OtherFlexibleOptions.TEST_OTHER_FLEXIBLE_OPTIONS,
            email = "test@soogong.co.kr",
        )

        val NULL_BASIC_INFORMATION = BasicInformation(
            subscriptionPlan = "",
            profileImage = ImagePath.NULL_IMAGE_PATH,
            myReviews = MyReview.NULL_MY_REVIEW,
            myPageUrl = "",
            portfolios =  emptyList(),
            priceByProjects = emptyList(),
            flexibleCost = FlexibleCost.NULL_FLEXIBLE_COST,
            otherFlexibleOptions = OtherFlexibleOptions.NULL_OTHER_FLEXIBLE_OPTIONS,
            email = "",
        )
    }
}