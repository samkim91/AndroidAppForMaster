package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterDto

@Parcelize
data class ReviewSummary(
    val averageRecommendation: Float,
    val averageKindness: Float,
    val averageQuality: Float,
    val averageAffordability: Float,
    val averagePunctuality: Float,
    val reviewCount: Int,
    val repairCount: Int,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): ReviewSummary =
            ReviewSummary(
                averageRecommendation = masterDto.reviewRecommendationAvg ?: 0.0f,
                averageKindness = masterDto.reviewKindnessAvg ?: 0.0f,
                averageQuality = masterDto.reviewQualityAvg ?: 0.0f,
                averageAffordability = masterDto.reviewAffordabilityAvg ?: 0.0f,
                averagePunctuality = masterDto.reviewPunctualityAvg ?: 0.0f,
                reviewCount = masterDto.reviewCount ?: 0,
                repairCount = masterDto.repairCount ?: 0,
            )
    }
}