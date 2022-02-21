package kr.co.soogong.master.data.datasource.network.requirement.review

import com.google.gson.JsonObject
import retrofit2.Retrofit
import javax.inject.Inject

class ReviewService @Inject constructor(
    retrofit: Retrofit,
) {
    private val reviewInterface = retrofit.create(ReviewInterface::class.java)

    suspend fun requestReview(repairId: Int): JsonObject {
        return reviewInterface.requestReview(repairId)
    }
}
