package kr.co.soogong.master.data.repository

import com.google.gson.JsonObject
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.datasource.network.requirement.review.ReviewService
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.requirement.review.ReviewDto
import java.net.HttpURLConnection
import javax.inject.Inject

@Reusable
class ReviewRepository @Inject constructor(
    private val reviewService: ReviewService,
) {

    fun getReviews(
        masterUid: String,
        offset: Int,
        pageSize: Int,
        order: Int,
        orderBy: String,
    ): Single<PageableContentDto<ReviewDto>> =
        reviewService.getReviews(masterUid, offset, pageSize, order, orderBy)
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK) {
                    responseDto.data?.let { pageableContentDto ->
                        PageableContentDto(
                            content = pageableContentDto.content,
                            pageable = pageableContentDto.pageable,
                            last = pageableContentDto.last,
                            numberOfElements = pageableContentDto.numberOfElements
                        )
                    }
                } else {
                    throw Exception(responseDto.messageKo)
                }
            }


    suspend fun requestReview(repairId: Int): JsonObject {
        return reviewService.requestReview(repairId)
    }

    companion object {
        private const val TAG = "ReviewRepository"
    }
}
