package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.estimation.ImagePath

@Parcelize
data class MyReview(
    val averageRecommendation: Double,
    val averageKindness: Double,
    val averageQuality: Double,
    val averageAffordability: Double,
    val averagePunctuality: Double,
    val reviews: List<Review> = emptyList(),
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): MyReview {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject

            val reviewsJson = attributes.get("reviews").asJsonArray
            val reviews = mutableListOf<Review>()
            for (item in reviewsJson) {
                reviews.add(Review.fromJson(item.asJsonObject))
            }

            return MyReview(
                averageRecommendation = attributes.get("average_recommendation").asDouble,
                averageKindness = attributes.get("average_kindness").asDouble,
                averageQuality = attributes.get("average_quality").asDouble,
                averageAffordability = attributes.get("average_affordability").asDouble,
                averagePunctuality = attributes.get("average_punctuality").asDouble,
                reviews = reviews
            )
        }

        val NULL_PRICE_BY_PROJECT = MyReview(
            4.8,
            4.9,
            4.6,
            4.8,
            4.8,
            listOf(Review.NULL_REVIEW, Review.NULL_REVIEW, Review.NULL_REVIEW)
        )
    }
}