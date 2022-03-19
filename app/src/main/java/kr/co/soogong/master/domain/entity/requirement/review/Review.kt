package kr.co.soogong.master.domain.entity.requirement.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.requirement.review.ReviewDto
import kr.co.soogong.master.domain.entity.DtoConverter
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

    companion object : DtoConverter<ReviewDto, Review> {
        override fun fromDto(dto: ReviewDto): Review {
            return Review(
                id = dto.id,
                customerName = dto.customerNickname ?: "익명",
                customerProfileImageUrl = dto.customerProfileImageUrl ?: "",
                projectName = dto.projectName ?: "",
                content = dto.content ?: "",
                images = dto.images ?: emptyList(),
                recommendation = dto.recommendation ?: 0.0f,
                kindness = dto.kindness ?: 0.0f,
                quality = dto.quality ?: 0.0f,
                affordability = dto.affordability ?: 0.0f,
                punctuality = dto.punctuality ?: 0.0f,
                createdAt = dto.createdAt,
            )
        }
    }
}
