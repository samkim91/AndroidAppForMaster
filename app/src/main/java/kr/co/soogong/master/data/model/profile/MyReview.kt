package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterDto
import kotlin.math.round

@Parcelize
data class MyReview(
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
                averageRecommendation = masterDto.reviews?.map { reviewDto ->
                    reviewDto.reviewScores?.find { reviewScore ->
                        reviewScore.scoreCode == "Recommendation"
                    }?.score!!
                }?.average()?.let { round(it * 10) / 10 },
                averageKindness = masterDto.reviews?.map { reviewDto ->
                    reviewDto.reviewScores?.find { reviewScore ->
                        reviewScore.scoreCode == "Kindness"
                    }?.score!!
                }?.average()?.let { round(it * 10) / 10 },
                averageQuality = masterDto.reviews?.map { reviewDto ->
                    reviewDto.reviewScores?.find { reviewScore ->
                        reviewScore.scoreCode == "Quality"
                    }?.score!!
                }?.average()?.let { round(it * 10) / 10 },
                averageAffordability = masterDto.reviews?.map { reviewDto ->
                    reviewDto.reviewScores?.find { reviewScore ->
                        reviewScore.scoreCode == "Affordability"
                    }?.score!!
                }?.average()?.let { round(it * 10) / 10 },
                averagePunctuality = masterDto.reviews?.map { reviewDto ->
                    reviewDto.reviewScores?.find { reviewScore ->
                        reviewScore.scoreCode == "Punctuality"
                    }?.score!!
                }?.average()?.let { round(it * 10) / 10 },
                reviews = masterDto.reviews?.map { reviewDto ->
                    Review.fromReviewDto(reviewDto)
                }
            )
        }
    }
}