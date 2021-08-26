package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterDto
import kotlin.math.round

@Parcelize
data class MyReview(
    val reviewCount: Int?,
    val averageRecommendation: Double?,
    val averageKindness: Double?,
    val averageQuality: Double?,
    val averageAffordability: Double?,
    val averagePunctuality: Double?,
    val reviews: List<Review>? = emptyList(),
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): MyReview {
            return MyReview(
                reviewCount = masterDto.reviewCount,
                averageRecommendation = masterDto.reviewRecommendationAvg,
                averageKindness = masterDto.reviewKindnessAvg,
                averageQuality = masterDto.reviewQualityAvg,
                averageAffordability = masterDto.reviewAffordabilityAvg,
                averagePunctuality = masterDto.reviewPunctualityAvg,
                reviews = masterDto.reviews?.map { reviewDto ->
                    Review.fromReviewDto(reviewDto)
                }
            )
        }
    }
}