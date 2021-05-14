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
            )
        }
    }
}