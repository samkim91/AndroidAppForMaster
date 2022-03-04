package kr.co.soogong.master.data.datasource.network.requirement.review

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.requirement.review.ReviewDto
import retrofit2.Retrofit
import javax.inject.Inject

class ReviewService @Inject constructor(
    retrofit: Retrofit,
) {
    private val reviewInterface = retrofit.create(ReviewInterface::class.java)

    fun getReviews(
        uid: String,
        offset: Int,
        pageSize: Int,
        order: Int,
        orderBy: String,
    ): Single<ResponseDto<PageableContentDto<ReviewDto>>> =
        reviewInterface.getReviews(uid, offset, pageSize, order, orderBy)

    suspend fun requestReview(repairId: Int): JsonObject {
        return reviewInterface.requestReview(repairId)
    }
}
