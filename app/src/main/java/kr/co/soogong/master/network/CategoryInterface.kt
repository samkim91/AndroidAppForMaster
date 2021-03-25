package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryInterface {
    @GET(HttpContract.CATEGORY)
    fun getCategoryList(): Single<List<JsonObject>>

    @GET(HttpContract.PROJECT)
    fun getProjectList(@Path("id") id: Int): Single<List<JsonObject>>
}