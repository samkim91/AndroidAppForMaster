package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.dto.requirement.review.ReviewDto
import kr.co.soogong.master.data.global.CodeTable
import java.util.*

@Parcelize
data class Review(
    val id: Int?,
    val customerName: String?,
    val customerImage: AttachmentDto?,
    val recommendation: ReviewScore?,
    val kindness: ReviewScore?,
    val quality: ReviewScore?,
    val affordability: ReviewScore?,
    val punctuality: ReviewScore?,
    val projectName: String?,
    val reviewContent: String?,
    val imageList: MutableList<AttachmentDto>?,
    val createdAt: Date,
) : Parcelable {
    companion object {
        fun fromReviewDto(reviewDto: ReviewDto): Review {
            return Review(
                id = reviewDto.id,
                customerName = reviewDto.customerName,
                customerImage = reviewDto.customerImage,
                recommendation = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == CodeTable.RECOMMENDATION.code }
                    ?.let { ReviewScore.fromReviewScoreDto(it) },
                kindness = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == CodeTable.KINDNESS.code }
                    ?.let { ReviewScore.fromReviewScoreDto(it) },
                quality = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == CodeTable.QUALITY.code }
                    ?.let { ReviewScore.fromReviewScoreDto(it) },
                affordability = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == CodeTable.AFFORDABILITY.code }
                    ?.let { ReviewScore.fromReviewScoreDto(it) },
                punctuality = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == CodeTable.PUNCTUALITY.code }
                    ?.let { ReviewScore.fromReviewScoreDto(it) },
                projectName = reviewDto.projectName,
                reviewContent = reviewDto.content,
                imageList = reviewDto.attachments,
                createdAt = reviewDto.createdAt,
            )
        }
    }
}