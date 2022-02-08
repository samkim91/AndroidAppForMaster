package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.requirement.review.ReviewDto
import java.util.*

@Parcelize
data class Review(
    val id: Int,
    val customerName: String,
    val customerProfileImageUrl: String,
    val projectName: String,
    val content: String,
    val images: List<String>,
    val recommendation: Float,
    val kindness: Float,
    val quality: Float,
    val affordability: Float,
    val punctuality: Float,
    val createdAt: Date,
) : Parcelable {
    companion object {
        fun fromReviewDto(reviewDto: ReviewDto): Review =
            Review(
                id = reviewDto.id,
                customerName = reviewDto.customerNickname ?: "익명",
                customerProfileImageUrl = reviewDto.customerProfileImageUrl ?: "",
                projectName = reviewDto.projectName ?: "",
                content = reviewDto.content ?: "",
                images = reviewDto.images ?: emptyList(),
                recommendation = reviewDto.recommendation ?: 0.0f,
                kindness = reviewDto.kindness ?: 0.0f,
                quality = reviewDto.quality ?: 0.0f,
                affordability = reviewDto.affordability ?: 0.0f,
                punctuality = reviewDto.punctuality ?: 0.0f,
                createdAt = reviewDto.createdAt,
            )
    }
}
