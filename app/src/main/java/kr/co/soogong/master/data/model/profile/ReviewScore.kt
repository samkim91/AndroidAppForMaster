package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.requirement.review.ReviewScoreDto
import kr.co.soogong.master.data.global.CodeTable

@Parcelize
data class ReviewScore(
    val id: Int,
    val scoreCode: CodeTable?,
    val score: Int,
) : Parcelable {
    companion object {
        fun fromReviewScoreDto(reviewScoreDto: ReviewScoreDto): ReviewScore =
            ReviewScore(
                id = reviewScoreDto.id,
                scoreCode = CodeTable.getCodeTableByCode(reviewScoreDto.scoreCode),
                score = reviewScoreDto.score?.toInt() ?: 0
            )
    }
}