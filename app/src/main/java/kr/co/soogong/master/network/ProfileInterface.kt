package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.profile.Portfolio
import retrofit2.http.*

interface ProfileInterface {
    @GET(HttpContract.GET_USER_PROFILE_V2)
    suspend fun getUserProfile(@Path("keycode") keycode: String?): JsonObject

    @GET(HttpContract.GET_USER_PROFILE_V2)
    fun getPortfolio(@Body body: HashMap<String, String>): JsonObject

    @POST(HttpContract.GET_USER_PROFILE_V2)
    fun setPortfolio(@Body body: HashMap<String, String>): Single<Response>
}