package kr.co.soogong.master.data.repository

import com.google.gson.JsonObject
import dagger.Reusable
import kr.co.soogong.master.data.datasource.network.requirement.review.ReviewService
import javax.inject.Inject

@Reusable
class ReviewRepository @Inject constructor(
    private val reviewService: ReviewService
) {

    suspend fun requestReview(repairId: Int): JsonObject {
        return reviewService.requestReview(repairId)
    }

    companion object {
        private const val TAG = "ReviewRepository"
    }
}
