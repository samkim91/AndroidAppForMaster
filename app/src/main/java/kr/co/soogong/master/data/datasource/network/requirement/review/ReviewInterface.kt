package kr.co.soogong.master.data.datasource.network.requirement.review

import com.google.gson.JsonObject
import kr.co.soogong.master.contract.HttpContract
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewInterface {

    @POST(HttpContract.REQUEST_REVIEW_V2)
    suspend fun requestReview(@Path("id") repairId: Int): JsonObject

}