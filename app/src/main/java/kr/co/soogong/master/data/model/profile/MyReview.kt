package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

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

            return MyReview(
                averageRecommendation = attributes.get("average_recommendation").asDouble,
                averageKindness = attributes.get("average_kindness").asDouble,
                averageQuality = attributes.get("average_quality").asDouble,
                averageAffordability = attributes.get("average_affordability").asDouble,
                averagePunctuality = attributes.get("average_punctuality").asDouble,
                reviews = attributes.get("reviews").asJsonArray.map { Review.fromJson(it.asJsonObject) }
            )
        }

        val TEST_MY_REVIEW = MyReview(
            4.8,
            4.9,
            4.6,
            4.8,
            4.8,
            listOf(Review.TEST_REVIEW, Review.TEST_REVIEW, Review.TEST_REVIEW)
        )

        val NULL_MY_REVIEW = MyReview(
            0.0, 0.0, 0.0, 0.0, 0.0, emptyList()
        )
    }
}