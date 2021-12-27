package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterDto

@Parcelize
data class MyReview(
    val averageRecommendation: Double?,
    val reviewCount: Int?,
    val repairCount: Int?,
    val averageKindness: Double?,
    val averageQuality: Double?,
    val averageAffordability: Double?,
    val averagePunctuality: Double?,
    val reviews: List<Review>? = emptyList(),
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): MyReview {
            return MyReview(
                averageRecommendation = masterDto.reviewRecommendationAvg,
                reviewCount = masterDto.reviewCount,
                repairCount = masterDto.repairCount,
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