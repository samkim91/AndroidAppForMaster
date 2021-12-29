package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.data.dto.requirement.review.ReviewDto
import java.util.*

@Parcelize
data class Review(
    val id: Int?,
    val recommendation: Int?,
    val kindness: Int?,
    val quality: Int?,
    val affordability: Int?,
    val punctuality: Int?,
    val projectType: String?,
    val reviewContent: String?,
    val imageList: MutableList<AttachmentDto>?,
    val createdAt: Date?,
) : Parcelable {
    companion object {
        fun fromReviewDto(reviewDto: ReviewDto): Review {
            return Review(
                id = reviewDto.id,
                recommendation = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == "Recommendation" }?.score?.toInt(),
                kindness = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == "Kindness" }?.score?.toInt(),
                quality = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == "Quality" }?.score?.toInt(),
                affordability = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == "Affordability" }?.score?.toInt(),
                punctuality = reviewDto.reviewScores?.find { reviewScore -> reviewScore.scoreCode == "Punctuality" }?.score?.toInt(),
                projectType = reviewDto.projectType,
                reviewContent = reviewDto.content,
                imageList = reviewDto.attachments,
                createdAt = reviewDto.createdAt,
            )
        }
    }
}