package kr.co.soogong.master.network

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInterface {
    @GET(HttpContract.GET_USER_PROFILE)
    fun getUserProfile(@Path("keycode") keycode: String?): Single<JsonObject>
}