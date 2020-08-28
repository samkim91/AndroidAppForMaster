package kr.co.soogong.master.util.http

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpInterface {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("language") language: String
    ): Flowable<String>
}