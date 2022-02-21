package kr.co.soogong.master.domain.usecase.requirement

import com.google.gson.JsonObject
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.repository.ReviewRepository
import javax.inject.Inject

@Reusable
class RequestReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
) {
    suspend operator fun invoke(repairId: Int): JsonObject {
        return withContext(Dispatchers.IO) {
            reviewRepository.requestReview(repairId)
        }
    }
}