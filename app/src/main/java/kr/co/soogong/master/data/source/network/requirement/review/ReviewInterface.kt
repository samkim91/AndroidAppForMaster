package kr.co.soogong.master.data.source.network.requirement.review

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.requirement.review.ReviewDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewInterface {

    @GET(HttpContract.GET_REVIEWS)
    fun getReviews(
        @Query("uid") uid: String,
        @Query("offset") offset: Int,
        @Query("pageSize") pageSize: Int,
        @Query("order") order: Int,
        @Query("orderBy") orderBy: String,
    ): Single<ResponseDto<PageableContentDto<ReviewDto>>>

    @POST(HttpContract.REQUEST_REVIEW_V2)
    suspend fun requestReview(@Path("id") repairId: Int): JsonObject

}