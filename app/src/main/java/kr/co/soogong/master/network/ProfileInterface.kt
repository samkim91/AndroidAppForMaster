package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import kr.co.soogong.master.contract.HttpContract
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileInterface {
    @GET(HttpContract.GET_MASTER_PROFILE)
    fun getProfile(@Path("id") id: String?): Single<JsonObject>

    @GET(HttpContract.GET_USER_PROFILE_V2)
    fun getPortfolio(@Body body: HashMap<String, String>): JsonObject

    @POST(HttpContract.GET_USER_PROFILE_V2)
    fun savePortfolio(@Body body: HashMap<String, String>): Single<Response>

    @GET(HttpContract.GET_USER_PROFILE_V2)
    fun getPriceByProject(@Body body: HashMap<String, String>): JsonObject

    @POST(HttpContract.GET_USER_PROFILE_V2)
    fun savePriceByProject(@Body body: HashMap<String, String>): Single<Response>
}